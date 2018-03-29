package com.example.cs4532.umdalive;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class RestCalls {

    String url = "ukko.d.umn.edu:32892";

    RequestQueue queue;

    public RestCalls(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public JSONObject getClub (String clubID){
        JSONObject serverResponse;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("clubID", clubID)
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url + "/getUser", map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        serverResponse  = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        return serverResponse;
    }
/*

GetUser(userEmail)

GetClub(clubID)

JoinClub(userEmial, clubID)

createClub(clubObject)

GetEvent(eventId)

CreateEvent(eventData)


 */
}
