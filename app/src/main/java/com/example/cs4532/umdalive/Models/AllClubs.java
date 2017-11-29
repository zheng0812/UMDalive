package com.example.cs4532.umdalive.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Andy Miller 3/21/2017
 */

public class AllClubs {
    public static ArrayList<String> clubList = new ArrayList<>();
    /**
     * This is used to convert a JSON response into a list of strings containing the names of the clubs.
     *
     * @param jsonResponse the response from the REST get call to the server retrieving all clubs.
     * @return ArrayList of all club names
     */
    public static ArrayList<String> getClubNames(String jsonResponse) {
        //ArrayList<String> clubList = new ArrayList<>();
        Log.d("clubs: ", jsonResponse);
        try {
            JSONObject object = new JSONObject(jsonResponse);
            JSONArray jsonArray = object.getJSONArray("items");
            if (jsonArray != null) {
                int len = jsonArray.length();
                clubList.clear();
                for (int i = 0; i < len; i++) {
                    clubList.add(jsonArray.get(i).toString());
                    Log.d(jsonArray.get(i).toString(), jsonArray.get(i).toString());
                }
                Collections.sort(clubList, String.CASE_INSENSITIVE_ORDER);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clubList;
    }

}