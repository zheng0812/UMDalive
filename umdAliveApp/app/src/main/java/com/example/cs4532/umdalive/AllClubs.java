package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
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

    private TextView clubNames;

    private LinearLayout clubs;

    public AllClubs (Activity a, Context c){
        activity = a;
        context = c;

        clubNames = (TextView) activity.findViewById(R.id.clubNamesView);
        rest = new RestCalls(context);
    }

    public void buildPage () {

    }
    public void buildPage (String clubId)  throws JSONException {
        rest.getClub(clubId, new CallBack() {
            @Override
            public void callBack(JSONObject serverResponse) throws JSONException {
                updateUI(serverResponse);
            }
        });
    }

    private void updateUI(JSONObject clubData){}


    @Override
    public void onClick(View v) {

    }
}
