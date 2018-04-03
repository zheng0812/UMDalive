package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile implements View.OnClickListener{

    private Activity activity;

    private Context context;

    private TextView profileName;

    private TextView profileMajor;

    private final String url = "http://ukko.d.umn.edu:32892/getUser/";

    private RequestQueue queue;

    private JSONObject profileData;

    public Profile(Activity a, Context c) {
        activity = a;
        context = c;

        profileName = (TextView) activity.findViewById(R.id.profileName);
        profileMajor = (TextView) activity.findViewById(R.id.profileMajor);

        queue = Volley.newRequestQueue(context);
    }

    public void buildPage (String clubId)  throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + clubId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            profileData = new JSONObject(response);

                            profileName.setText(profileData.getString("name"));
                            profileMajor.setText(profileData.getString("description"));
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

    @Override
    public void onClick(View v) {

    }
}


