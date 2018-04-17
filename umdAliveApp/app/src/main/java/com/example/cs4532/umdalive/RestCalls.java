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
import java.util.concurrent.Callable;
import java.util.function.Function;

public class RestCalls {
    String url = "http://ukko.d.umn.edu:32892/";


    RequestQueue queue;

    JSONObject serverResponse;

    public RestCalls(Context context) {
        queue = Volley.newRequestQueue(context);
    }


    public void getClub(String clubID, final CallBack callBack) throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "getClub/" + clubID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            serverResponse = new JSONObject(response);
                            callBack.callBack(serverResponse);
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
    }

    public void getUser(String userID, final CallBack callBack) throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "getUser/" + userID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            serverResponse = new JSONObject(response);
                            callBack.callBack(serverResponse);
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
    }

    public void getAllClubs(final CallBack callBack) throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "getAllClubs",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            serverResponse = new JSONObject(response);
                            callBack.callBack(serverResponse);
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
    }
}


