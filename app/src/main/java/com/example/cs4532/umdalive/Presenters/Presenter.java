package com.example.cs4532.umdalive.Presenters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import com.example.cs4532.umdalive.Models.AllClubs;
import com.example.cs4532.umdalive.Models.ClubInformationModel;
import com.example.cs4532.umdalive.Models.CreateClub;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.Views.DeleteClubView;
import com.example.cs4532.umdalive.Views.LoginView;
import com.example.cs4532.umdalive.Models.MainActivity;
import com.example.cs4532.umdalive.Models.PostAdapter;
import com.example.cs4532.umdalive.Models.PostInformationModel;
import com.example.cs4532.umdalive.Models.RestModel;
import com.example.cs4532.umdalive.Models.UserInformationModel;
import com.example.cs4532.umdalive.Views.UserDataView;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ryan_000 on 3/15/2017.
 * <p>
 * Presenter class to communicate between views and models
 */

public class Presenter {
    private Activity activity;
    private RestModel restModel;
    private String thisUser; //current user's email


    /**
     * Constructor for presenter
     *
     * @param incomingActivity view that created presenter.
     *                         <p>
     *                         creates a RestModel for node communication
     */
    public Presenter(Activity incomingActivity) {
        restModel = new RestModel();
        activity = incomingActivity;
    }

    /**
     * Constructor for testing RestModel that requires no view
     */
    public Presenter() {
        activity = null;
        restModel = new RestModel();

    }

    public String getThisUser(){return thisUser;}

    public void setThisUser(String curUser){
        thisUser = curUser;
        //restPut("putNewUser", curUser);
    }

    private Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private RestModel getRestModel() {
        return restModel;
    }

    /**
     * Rest Function sends parameters to RestModel where they are dealt with using switch statement.
     *
     * @param task to be performed
     * @param data data to be handle
     * @return Currently returns a string to represent what could be returned
     */
    public String restPost(String task, String data) {
        return restModel.restPost(task, data);
    }

    /**
     * Rest Function sends parameters to RestModel where they are dealt with using switch statement.
     *
     * @param task to be performed
     * @param data data to be handle
     * @return Currently returns a string to represent what could be returned
     */
    public String restPut(String task, String data) {
        return restModel.restPut(task, data);
    }

    /**
     * Rest Function sends parameters to RestModel where they are dealt with using switch statement.
     *
     * Used to delete a club
     *
     * @param task to be performed
     * @param data data to be handle
     */
    public boolean restDelete(String task, String data)
    {
        return restModel.restDelete(task, data);
    }

    /**
     * Rest Function sends parameters to RestModel where they are dealt with using switch statement.
     *
     * @param task  to be performed
     * @param toGet data to get
     * @return Currently returns a string to represent what could be returned
     */
    public String restGet(String task, String toGet) {
        return restModel.restGet(task, toGet);
    }

    /**
     * Used to create a new club object to be sent to the server.
     *
     * @param clubName    name of club
     * name of admin is hard coded
     * @param keyWords    tags
     * @param ownerEmail  club owner email
     * @param description description of club
     * @return string version of the JSON package.
     */
    public String makeClub(String clubName, String keyWords, String ownerEmail, String description) {
       // getMainUser(restModel.restGet("getUserEmail",ownerEmail)).setUserType(clubName);
        return CreateClub.makeClub(clubName, ownerEmail, keyWords, ownerEmail, description);//, initialPost);
    }

    /**
     * For MainActivity
     *
     * @param userData to get
     * @return user
     */
    public UserInformationModel getMainUser(String userData){
        return MainActivity.getUser(userData);
    }

    /**
     * For MainActivity
     *
     * @param jsonString restGet results for most recent posts
     * @return list of post objects
     */
    public ArrayList<PostInformationModel> refreshPosts(String jsonString) {
        return MainActivity.refreshPosts(jsonString);
    }

    public void putPost(String club, String title, String time, String date, String location, String addInfo, String image, String clubOwner) {
        restPut("putNewPost", PostInformationModel.jsonStringify(club, title, time, date, location, addInfo, image, clubOwner));
    }

    public void putUser(String name, String major, String email, String gradDate) {
        UserDataView user = new UserDataView();

        restPut("putNewUser", UserInformationModel.jsonStringify(name, major, email, gradDate, "club member", user.getmSelectedItems()));
    }

    /**
     * gets the userEmail
     * @return the main userEmail
     */
    public String getUserEmail(){
        return getMainUser(restModel.restGet("getUserEmail",thisUser)).getName();
    }
    /**
     * gets all the club names
     *
     * @return list of all clubs
     */
    public ArrayList<String> getClubNames() {
        return AllClubs.getClubNames(restGet("getAllClubs", ""));
    }

    /**
     * gets the clubOwner of a specified club
     *
     * @return clubOwner
     */
    public boolean checkIfClubOwner(String clubName){

        //return (getMainUser((restGet("getUserEmail", thisUser))).getUserType().equals(clubName));
        return true;
    }

    /**
     * used to get all clubs with a keyword
     *
     * @param keyword to search
     * @return clubs containing keywords
     */
    public ArrayList<String> getSearchClubNames(String keyword) {
        return AllClubs.getClubNames(restGet("getSearchAllClubs", keyword));
    }

    public PostAdapter getPostAdapter(ArrayList<PostInformationModel> posts, RecyclerView rView) {
        return new PostAdapter(posts, rView);
    }

    /**
     * testing
     *
     * @param presenter to compare
     * @return if presenter is equal
     */
    public boolean equals(Presenter presenter) {
        boolean isEqual = true;
        if (!activity.equals(presenter.getActivity())) isEqual = false;
        if (!restModel.equals(presenter.getRestModel())) isEqual = false;
        return isEqual;
    }

    /**
     * Checks if a string is a valid form of input
     *
     * @param str input
     * @return false if invalid
     */
    public boolean isClubInfoValid(String str) {
        return ClubInformationModel.checkAscii(str);
    }

    /**
     * Checks if a string is a valid form of input
     *
     * @param str input
     * @return false if invalid
     */
    public boolean isClubNameValid(String str) {
        return ClubInformationModel.checkClubNameAscii(str);
    }

    /**
     * Checks if a string is a valid form of input
     *
     * @param str input
     * @return false if invalid
     */
    public boolean isPostInfoValid(String str) {
        return PostInformationModel.checkAscii(str);
    }

    /**
     * Sets a variable to the current user's email logged in when a user logs in
     * @param userEmail
     */
//    public void setCurUser(String userEmail) {this.curUser = userEmail;}
    /**
     * Gets the email of the current user logged in
     * @return curUser
     */
//    public String getCurUser(){return curUser;    }

}