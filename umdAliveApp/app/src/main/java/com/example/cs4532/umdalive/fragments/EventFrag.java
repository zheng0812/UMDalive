package com.example.cs4532.umdalive.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Josh on 4/24/2018.
 */

public class EventFrag extends Fragment implements View.OnClickListener{

    //View
    View view;

    //Layout Components
    private TextView eventName;
    private TextView eventDescription;
    private TextView eventDate;
    private TextView eventTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create View
        view = inflater.inflate(R.layout.club_layout, container, false);

        //Get Layout Components
        getLayoutComponents();

        //Use Volley Singleton to Update Page UI
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restSingleton.getUrl() + "getEvent/" + getArguments().getString("eventID"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            updateUI(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error connecting", String.valueOf(error));
            }
        });
        restSingleton.addToRequestQueue(stringRequest);

        //Return View
        return view;

    }

    @Override
    public void onClick(View v) {

    }

    private void getLayoutComponents() {
        eventName=view.findViewById(R.id.EventNameView);
        eventDate=view.findViewById(R.id.EventDateView);
        eventDescription=view.findViewById(R.id.EventDescriptionView);
        eventTime=view.findViewById(R.id.EventTimeView);
    }

    private void updateUI(JSONObject res) throws JSONException{
        eventName.setText(res.getString("name"));
        eventDate.setText(res.getString("date"));
        eventDescription.setText(res.getString("description"));
        eventTime.setText(res.getString("time"));
    }

}