package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AllClubs implements View.OnClickListener{

    private Activity activity;
    private Context context;
    private RestCalls rest;

    private TextView clubName;

    public AllClubs (Activity a, Context c){
        activity = a;
        context = c;
        rest = new RestCalls(context);
    }

    public void buildPage () {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + clubId,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            clubData = new JSONObject(response);
//
//                            clubName.setText(clubData.getString("name"));
//                            clubDescription.setText(clubData.getString("description"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error);
//            }
//        });
//
//        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

    }
}
