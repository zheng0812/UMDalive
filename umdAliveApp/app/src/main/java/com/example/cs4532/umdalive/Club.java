package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Club implements View.OnClickListener{

    private Activity activity;

    private Context context;

    private RestCalls rest;

    private TextView clubName;

    private TextView clubDescription;

    private JSONObject blowjob;

    public Club (Activity a, Context c) throws JSONException {
        activity = a;
        context = c;
        rest = new RestCalls(context);
        clubName = (TextView) activity.findViewById(R.id.ClubNameView);
        clubDescription = (TextView) activity.findViewById(R.id.DescriptionView);
        blowjob = rest.getClub("Test");
        clubName.setText(blowjob.getString("name"));
        clubDescription.setText(blowjob.getString("description"));
    }

    public void buildPage () {
    }

    @Override
    public void onClick(View v) {

    }
}
