package com.example.cs4532.umdalive.Models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Andrew Miller on 3/15/2017.
 * <p>
 * Rest Class to perform all server interaction
 * <p>
 * communication is done from view to presenter to RestModel.
 */

public class RestModel {
    //public final String serverAddress = "http://127.0.0.1:60000";//Possible personal debugging
    public final String serverAddress = "http://131.212.41.37:65000"; //Ellie's Mongo
    //10.0.2.2:5000
    //public final String serverAddress = "http://131.212.41.37:5004"; //Permanent IP
    ArrayList<PostInformationModel> myPostArray;

    private Context context;

    /**
     * The context might be used later debugging with toast messages. Right now it is not needed though.
     *
     * @param context for toast
     */
    public RestModel(Context context) {
        this.context = context;
        myPostArray = new ArrayList<PostInformationModel>();
    }

    /**
     * Constructor for RestModel
     */
    public RestModel() {
        context = null;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * switch statement to handle REST GETS
     *
     * @param getString what we are getting
     * @param data      data to get.
     * @return data from server
     */
    public String restGet(String getString, String data) {
        switch (getString) {
            case "getAllClubs":
                return getAllClubs();
            case "getSearchAllClubs":
                return getSearchAllClubs(data);
            case "getClub":
                return getCurrentClub(data);
            case "getRecentPosts":
                return getRecentPosts();
            case "getUserData":
                return getUserData();
            case "getUserEmail":
                return getUserEmail(data);
            default:
                return null;
        }
    }

    /**
     * not used
     *
     * @param postString task
     * @param data       to post
     * @return null
     */
    public String restPost(String postString, String data) {
        return null;
    }

    public String restPut(String putString, String data) {
        switch (putString) {
            case "putNewClub":
                putNewClub(data);
                break;
            case "putNewPost":
                putNewPost(data);
                break;
            case "putNewUser":
                putNewUser(data);
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * unused
     *
     * @param deleteString task
     * @param data         to delete
     * @return null
     */
    public boolean restDelete(String deleteString, String data) {

        boolean didRequest = false;
        switch (deleteString) {
            case "deleteClub":
                didRequest = deleteRequest(data);
                break;
            case "deleteUser":
                didRequest = deleteUser(data);
                break;
            case "deletePost":
                didRequest = deletePost(data);
        }
        return didRequest;
    }

    /**
     * testing method
     *
     * @param restModel rest to test
     * @return boolean is equal
     */
    public boolean equals(RestModel restModel) {
        boolean isEquals = true;
        if (!serverAddress.equals(restModel.serverAddress)) isEquals = false;
        return isEquals;
    }


    /**
     * Used by DisplayClub and PostingActivity to fetch the club selected by the user in the previous view.
     *
     * @param data to get
     * @return current club
     */
    private String getCurrentClub(String data) {
        try {
            data = data.replace("/", "_");
            Log.d(data, data);
            return new HTTPAsyncTask().execute(serverAddress + "/clubs/" + data, "GET").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all clubs from server
     *
     * @return data of all clubs
     */
    private String getAllClubs() {
        try {
            return new HTTPAsyncTask().execute(serverAddress + "/clubs", "GET").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data keyword to search for
     * @return searched for club
     */
    private String getSearchAllClubs(String data) {
        try {
            return new HTTPAsyncTask().execute(serverAddress + "/clubSearch/" + data, "GET").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * From MainActivity refreshPosts function.
     *
     * @return JSON data of recent posts
     */
    private String getRecentPosts() {
        String mostRecentPosts = null;
        try {
            mostRecentPosts = new HTTPAsyncTask().execute(serverAddress + "/posts", "GET").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.out.println("Caught in getRecentPosts");
        }
        return mostRecentPosts;
    }

    /**
     * From MainActivity
     *
     * @return JSON data of user info
     */
    private String getUserData() {
        String userData;
        try {
            userData = new HTTPAsyncTask().execute(serverAddress + "/userData", "GET").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            userData = null;
        }
        return userData;
    }

    /**
     *
     */
    private String getUserEmail(String data){
        try {
        data = data.replace("/", "_");
        Log.d(data, data);
        return new HTTPAsyncTask().execute(serverAddress + "/users/" + data, "GET").get();
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
        return null;
}


    /**
     * Taken from Club.java
     *
     * @param data name of the club being fetched
     */
    private void putNewClub(String data) {
        new HTTPAsyncTask().execute(serverAddress + "/clubs", "PUT", data);
    }

    /**
     * Taken from PostingActivity
     *
     * @param data the post to be made
     */
    private void putNewPost(String data) {
        new HTTPAsyncTask().execute(serverAddress + "/posts", "PUT", data);
    }

    /**
     * put a new user
     *
     * @param data user
     */
    private void putNewUser(String data) {
        //jsonStringify(data); possibly get
        new HTTPAsyncTask().execute(serverAddress + "/userData", "PUT", data);
    }

    /**
     * Deletes a club from the collection of clubs
     *
     * @param data the delete to be made
     * @return foundClub    if the club was successfully deleted
     */
    public boolean deleteRequest(String data) {
        boolean foundClub = false;
        AllClubs listOfClubs = new AllClubs();
        JSONObject json = new JSONObject();

        try{
            json.put("clubName" , data);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        ArrayList<String> myList = listOfClubs.getClubNames(this.getAllClubs());
        for (String s: myList)
        {
            try {
                if (s.equals(json.getString("clubName").toString())) {
                    new HTTPAsyncTask().execute(serverAddress + "/delete", "DELETE", json.toString());
                    foundClub = true;
                }
            }
            catch(JSONException je){
                Log.d("JSONException: ", je.toString());
            }
        }
        return foundClub;
    }

    /**
     * Deletes a post from the collection of posts
     *
     * @param data the delete to be made
     * @return foundPost    if the post was successfully deleted
     */
    public boolean deletePost(String data){
        JSONObject json = new JSONObject();

        try{
            json.put("title" , data);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (PostInformationModel myPost: myPostArray) {

            if (myPost.getTitle().equals(data)) {
                new HTTPAsyncTask().execute(serverAddress + "/deletePost", "DELETE", json.toString());
                return true;
            }
        }

        return false;
    }

    /**
     * Deletes a user from the collection of users
     * Currently, this function is not implemented. deleteUser will always return false, but
     * the deleteUser commands work in index.js and mongoDBFunctions.js, so a user could be
     * deleted using Postman if necessary.
     *
     * @param data the delete to be made
     * @return foundUser    if the user was successfully deleted
     */
    public boolean deleteUser(String data){
        boolean foundUser = false;
        JSONObject json = new JSONObject();

        try{
            json.put("userName" , data);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        new HTTPAsyncTask().execute(serverAddress + "/deleteUser", "DELETE", data.toString());
        return foundUser;
    }

    public void setPostArray(ArrayList<PostInformationModel> posts)
    {
        myPostArray = posts;
    }

    private class HTTPAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection serverConnection = null;
            InputStream is;

            Log.d("Debug:", "Attempting to connect to: " + params[0]);

            try {
                URL url = new URL(params[0]);
                serverConnection = (HttpURLConnection) url.openConnection();
                serverConnection.setRequestMethod(params[1]);

                if (params[1].equals("PUT")) {
                    if (params[1].equals("PUT")) {
                        Log.d("DEBUG PUT:", "In put: params[0]=" + params[0] + ", params[1]=" + params[1] + ", params[2]=" + params[2]);
                        serverConnection.setDoOutput(true);
                        serverConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        serverConnection.setRequestProperty("Content-Length", "" +
                                Integer.toString(params[2].getBytes().length));
                        DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
                        out.writeBytes(params[2]);
                        out.flush();
                        out.close();
                    }
                }
                if (params[1].equals("DELETE")) {
                    Log.d("DEBUG DELETE:", "In delete: params[0]=" + params[0] + ", params[1]=" + params[1] + ", params[2]=" + params[2]);

                    serverConnection.setDoOutput(true);
                    serverConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    serverConnection.setRequestProperty("Content-Length", "" +
                            Integer.toString(params[2].getBytes().length));
                    DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
                    out.writeBytes(params[2]);
                    out.flush();
                    out.close();
                }

                int responseCode = serverConnection.getResponseCode(); //the connection is failing here
                Log.d("Debug:", "\nSending " + params[1] + " request to URL : " + params[0]);
                Log.d("Debug: ", "Response Code : " + responseCode);
                is = serverConnection.getInputStream();

                if (params[1].equals("GET")) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    try {
                        JSONObject jsonData = new JSONObject(sb.toString());
                        return jsonData.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("JSONException was caught");

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An IOException was caught");
            } finally {
                assert serverConnection != null;
                serverConnection.disconnect();
            }
            return "Should not get to this if the data has been sent/received correctly!";
        }

        protected void onPostExecute(String result) {
            Log.d("onPostExecute JSON: ", result);
        }
    }
}