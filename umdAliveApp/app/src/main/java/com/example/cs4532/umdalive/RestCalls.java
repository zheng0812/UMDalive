package com.example.cs4532.umdalive;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RestCalls {

    String url = "http://ukko.d.umn.edu:32892/getClub/test";

    RequestQueue queue;

    JSONObject serverResponse;

    public RestCalls(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public JSONObject getClub (String clubID) throws JSONException {

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            serverResponse = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        queue.add(stringRequest);
        while (serverResponse == null) {}
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
