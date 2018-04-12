package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
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

    private boolean membersGenerated;

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

        membersGenerated=false;

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
        if(clubData.getString("description")!=null)
            clubDescription.setText(clubData.getString("description"));
        else
            clubDescription.setVisibility(View.INVISIBLE);
        JSONObject memberJson = clubData.getJSONObject("members");
        JSONArray regulars = memberJson.getJSONArray("regular");
        JSONArray admins = memberJson.getJSONArray("admin");
        if(membersGenerated==false) {
            for (int i = 0; i < admins.length(); i++) {
                LinearLayout hl = new LinearLayout(context);
                hl.setOrientation(LinearLayout.HORIZONTAL);
                String name = admins.getString(i);
                TextView member = new TextView(context);
                TextView admin = new TextView(context);
                admin.setText("admin");
                admin.setTextSize(18);
                admin.setGravity(Gravity.RIGHT);
                admin.setWidth(members.getWidth() / 2);
                member.setText(name);
                member.setTextSize(18);
                member.setTextColor(Color.BLACK);
                member.setWidth(members.getWidth() / 2);
                member.setOnClickListener(this);
                hl.addView(member);
                hl.addView(admin);
                members.addView(hl);
            }
            for (int i = 0; i < regulars.length(); i++) {
                String name = regulars.getString(i);
                TextView member = new TextView(context);
                member.setText(name);
                member.setTextSize(
                        8);
                member.setTextColor(Color.BLACK);
                member.setOnClickListener(this);
                members.addView(member);
            }
            membersGenerated=true;
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println("clicked");
    }
}
