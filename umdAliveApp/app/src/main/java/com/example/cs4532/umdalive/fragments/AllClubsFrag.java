package com.example.cs4532.umdalive.fragments;

import android.os.Debug;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllClubsFrag extends Fragment implements View.OnClickListener {

    //View
    View view;

    //Layout Components
    private LinearLayout allClubsLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view =  inflater.inflate(R.layout.all_club_layout, container, false);

        //Get Layout Components
        getLayoutComponents();

        //Use Volley Singleton to Update Page UI
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restSingleton.getUrl() + "getAllClubs",
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

        //Return the View
        return view;
    }

    @Override
    public void onClick(View clickedView) {

    }

    private void getLayoutComponents () {
        allClubsLinearLayout = (LinearLayout) view.findViewById(R.id.AllClubsLayout);
    }

    private void updateUI(JSONObject res) throws JSONException {
        JSONArray allClubs = res.getJSONArray("clubs");
        for (int i=0;i<allClubs.length();i++) {
            String name = allClubs.getJSONObject(i).getString("name");
            TextView clubName = new TextView(view.getContext());
            clubName.setText(name);
            clubName.setTextSize(36);
            clubName.setOnClickListener(this);
            allClubsLinearLayout.addView(clubName);
        }
    }

}
