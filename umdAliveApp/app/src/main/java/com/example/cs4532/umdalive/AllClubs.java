package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class AllClubs implements View.OnClickListener{

    private Activity activity;
    private Context context;
    private RestCalls rest;

    public AllClubs (Activity a, Context c){
        activity = a;
        context = c;
        rest = new RestCalls(context);
    }

    public void buildPage () {

    }

    @Override
    public void onClick(View v) {

    }
}
