package com.example.cs4532.umdalive.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by meggi on 11/8/2017.
 */

public class DeleteClub {
    private static AllClubs listofAllClubs = new AllClubs();


    /**
     * @param clubName    name of club
     * @return if the club was properly deleted
     */
    public static boolean deleteClub(String clubName) {
        //find and delete the club
        boolean isDeleted = false;
        ArrayList<String> clubList = listofAllClubs.clubList;
        for (String s: clubList)
        {
            if (s.equals(clubName))
            {
                clubList.remove(s);
                isDeleted = true;
            }
        }

        return isDeleted;
    }
}
