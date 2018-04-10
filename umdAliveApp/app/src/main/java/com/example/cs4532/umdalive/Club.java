package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
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

    private RestCalls rest;


    private TextView clubName;

    private TextView clubDescription;

    private LinearLayout members;

    private LinearLayout events;


    public Club (Activity a, Context c) {
        activity = a;
        context = c;

        clubName = (TextView) activity.findViewById(R.id.ClubNameView);
        clubDescription = (TextView) activity.findViewById(R.id.DescriptionView);
        members = (LinearLayout) activity.findViewById(R.id.MembersLayout);

        rest = new RestCalls(context);
    }

    public void buildPage (String clubId)  throws JSONException {
        rest.getClub(clubId, new CallBack() {
            @Override
            public void callBack(JSONObject serverResponse) throws JSONException {
                updateUI(serverResponse);
            }
        });
    }

    private void updateUI(JSONObject clubData) throws JSONException {
        clubName.setText(clubData.getString("name"));
        clubDescription.setText(clubData.getString("description"));
        JSONObject memberJson = clubData.getJSONObject("members");
        JSONArray regulars = memberJson.getJSONArray("regular");
        JSONArray admins = memberJson.getJSONArray("admin");
        for (int i=0;i<admins.length();i++){
            String name = admins.getString(i);
            CardView memberCard = new CardView(context);
            Button memberButton = new Button(context);
            TextView memberName = new TextView(context);
            memberName.setText(name);
            //memberButton.setVisibility(View.INVISIBLE);
            memberButton.setOnClickListener(this);
            memberCard.addView(memberButton);
            memberCard.addView(memberName);
            members.addView(memberCard);
        }
        for (int i=0;i<regulars.length();i++){
            String name = regulars.getString(i);
            Button memberName = new Button(context);
            memberName.setText(name);
            members.addView(memberName);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
