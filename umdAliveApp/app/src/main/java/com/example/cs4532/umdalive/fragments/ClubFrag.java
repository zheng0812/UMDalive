package com.example.cs4532.umdalive.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
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

/**
 * Requires argument with key of clubID to be passed into it before it is added to the frame layout
 */

public class ClubFrag extends Fragment implements View.OnClickListener{

    //View
    View view;

    //Layout Components
    private TextView clubName;
    private TextView clubDescription;
    private LinearLayout members;
    private LinearLayout events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.club_layout, container, false);

        //Get Layout Components
        getLayoutComponents();

        //Use Volley Singleton to Update Page UI
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        System.out.println(restSingleton.getUrl() + "getClub/" + getArguments().getString("clubID"));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restSingleton.getUrl() + "getClub/" + getArguments().getString("clubID"),
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
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,).commit();
    }

    private void getLayoutComponents() {
        clubName = (TextView) view.findViewById(R.id.ClubNameView);
        clubDescription = (TextView) view.findViewById(R.id.DescriptionView);
        members = (LinearLayout) view.findViewById(R.id.MembersLayout);
    }

    private void updateUI(JSONObject res) throws JSONException{
        Log.d("clubtest",res.toString());
        clubName.setText(res.getString("name"));
        clubDescription.setText(res.getString("description"));
        JSONObject memberJson = res.getJSONObject("members");
        JSONArray regulars = memberJson.getJSONArray("regular");
        JSONArray admins = memberJson.getJSONArray("admin");
        for (int i=0;i<admins.length();i++){
            LinearLayout hl = new LinearLayout(view.getContext());
            hl.setOrientation(LinearLayout.HORIZONTAL);
            String name = admins.getString(i);
            TextView member = new TextView(view.getContext());
            TextView admin = new TextView(view.getContext());
            admin.setText("admin");
            admin.setTextSize(16);
            admin.setGravity(Gravity.RIGHT);
            admin.setWidth(members.getWidth()/2);
            member.setText(name);
            member.setTextSize(16);
            member.setWidth(members.getWidth()/2);
            member.setOnClickListener(this);
            hl.addView(member);
            hl.addView(admin);
            members.addView(hl);
        }
        for (int i=0;i<regulars.length();i++){
            String name = regulars.getString(i);
            TextView member = new TextView(view.getContext());
            member.setText(name);
            member.setTextSize(16);
            member.setOnClickListener(this);
            members.addView(member);
        }
    }
}
