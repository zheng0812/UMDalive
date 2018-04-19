package com.example.cs4532.umdalive.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Requires argument with key of userID to be passed into it before it is added to the frame layout
 */

public class ProfileFrag extends Fragment implements View.OnClickListener {

    //View
    View view;

    //Layout Components
    private TextView profileName;
    private TextView profileMajor;
    private TextView profileAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.profile_layout, container, false);

        //Get Layout Components
        getLayoutComponents();

        //Use Volley Singleton to Update Page UI
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restSingleton.getUrl() + "getUser/" + getArguments().getString("userID"),
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
    public void onClick(View clickedView) {

    }

    //Sets the Text views of the profile layout
    private void getLayoutComponents() {
        profileName = (TextView) view.findViewById(R.id.profileName);
        profileMajor = (TextView) view.findViewById(R.id.profileMajor);
        profileAbout = (TextView) view.findViewById(R.id.profileAbout);
    }

    //Updates the layout so current information is visible
    private void updateUI(JSONObject res) throws JSONException{
        getActivity().findViewById(R.id.PageLoading).setVisibility(View.GONE);
        profileName.setText(res.getString("name"));
        profileMajor.setText(res.getString("major"));
        profileAbout.setText(res.getString("about"));

    }
}
