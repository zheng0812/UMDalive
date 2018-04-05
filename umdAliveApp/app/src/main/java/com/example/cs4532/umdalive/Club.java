package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

import java.io.Console;

public class Club implements View.OnClickListener{

    private Activity activity;

    private Context context;


    private TextView clubName;

    private TextView clubDescription;

    private LinearLayout members;

    private LinearLayout events;

    private final String url = "http://ukko.d.umn.edu:32892/getClub/";

    private RequestQueue queue;

    private JSONObject clubData;

    public Club (Activity a, Context c) {
        activity = a;
        context = c;

        clubName = (TextView) activity.findViewById(R.id.ClubNameView);
        clubDescription = (TextView) activity.findViewById(R.id.DescriptionView);
        members = (LinearLayout) activity.findViewById(R.id.MembersText);

        queue = Volley.newRequestQueue(context);
    }

    public void buildPage (String clubId)  throws JSONException {
        Log.d("test", "eat my ass");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + clubId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            clubData = new JSONObject(response);
                            Log.d("server test",clubData.getString("name"));
                            clubName.setText(clubData.getString("name"));
                            clubDescription.setText(clubData.getString("description"));
                            JSONObject memberJson = clubData.getJSONObject("members");
                            JSONArray regulars = memberJson.getJSONArray("regular");
                            JSONArray admins = memberJson.getJSONArray("admin");
                            for (int i=0;i<admins.length();i++){
                                String name = admins.getString(i);
                                Button memberName = new Button(context);
                                memberName.setText(name);
                                members.addView(memberName);
                            }
                            for (int i=0;i<regulars.length();i++){
                                String name = regulars.getString(i);
                                Button memberName = new Button(context);
                                memberName.setText(name);
                                members.addView(memberName);
                            }
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
