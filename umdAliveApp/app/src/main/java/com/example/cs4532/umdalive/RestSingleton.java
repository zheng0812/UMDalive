package com.example.cs4532.umdalive;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RestSingleton {

    //Volley
    private static RestSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    //Server address
    private String url = "http://ukko.d.umn.edu:32892/";


    private RestSingleton (Context c) {
            context = c;
            requestQueue = getRequestQueue();
    }

    public static synchronized  RestSingleton getInstance(Context c) {
        if (instance == null) {
            instance = new RestSingleton(c);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    public String getUrl () {
        return url;
    }
}
