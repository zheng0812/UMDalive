package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllClubs implements View.OnClickListener{

    private Activity activity;
    private Context context;
    private RestCalls rest;

    private LinearLayout AllClubs;


    public AllClubs (Activity a, Context c){
        activity = a;
        context = c;

        AllClubs = (LinearLayout) activity.findViewById(R.id.AllClubsLayout);

        rest = new RestCalls(context);
    }

    public void buildPage()  throws JSONException {
        rest.getAllClubs(new CallBack() {
            @Override
            public void callBack(JSONObject serverResponse) throws JSONException {
                updateUI(serverResponse);
            }
        });
    }


    private void updateUI(JSONObject clubData) throws JSONException {
        JSONArray allClubs = clubData.getJSONArray("clubs");
        for (int i=0;i<allClubs.length();i++){
            String name = allClubs.getJSONObject(i).getString("name");
            TextView clubName = new TextView(context);
            clubName.setText(name);
            clubName.setOnClickListener(this);
            AllClubs.addView(clubName);
        }
    }


    @Override
    public void onClick(View v) {

    }
}
