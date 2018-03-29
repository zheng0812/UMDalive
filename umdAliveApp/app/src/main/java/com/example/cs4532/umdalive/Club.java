package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class Club implements View.OnClickListener{

    private Activity activity;

    private Context context;

    private RestCalls rest;

    private TextView clubName;

    private TextView description;

    public Club (Activity a, Context c) {
        activity = a;
        context = c;
        rest = new RestCalls(context);
        clubName = (TextView) activity.findViewById(R.id.ClubNameView);
        description = (TextView) activity.findViewById(R.id.DescriptionView);
        //rest.getClub("")
        clubName.setText("aids");
    }

    public void buildPage () {
    }

    @Override
    public void onClick(View v) {

    }
}
