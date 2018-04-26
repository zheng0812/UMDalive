package com.example.cs4532.umdalive.fragments.create;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.UserSingleton;
import com.example.cs4532.umdalive.fragments.base.ClubFrag;
import com.example.cs4532.umdalive.fragments.base.ProfileFrag;

import org.json.JSONException;
import org.json.JSONObject;


public class CreateClubFrag extends Fragment {
    View view;

    private EditText ClubImg;
    private EditText ClubName;
    private EditText ClubDescription;
    private Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.create_club_layout, container, false);

        getLayoutComponents();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClubImg.getText().length() > 0 && ClubName.getText().length() > 0 && ClubDescription.getText().length() > 0) {
                    try {
                        createClub();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    private void getLayoutComponents() {
        ClubName = view.findViewById(R.id.createClubName);
        ClubImg = view.findViewById(R.id.createClubImg);
        ClubDescription = view.findViewById(R.id.createClubDes);
        save = view.findViewById(R.id.createClubSave);
    }

    private void createClub() throws JSONException {
        JSONObject admin = new JSONObject();
        admin.put("name",UserSingleton.getInstance().getName());
        admin.put("userID",UserSingleton.getInstance().getUserID());
        JSONObject members = new JSONObject();
        String[] regulars = new String[0];
        members.put("admin",admin);
        members.put("regular",regulars);

        String[] events = new String[0];

        JSONObject newClubData = new JSONObject();
        newClubData.put("name", ClubName.getText());
        newClubData.put("description", ClubDescription.getText());
        newClubData.put("profilePic", ClubImg.getText());
        newClubData.put("events", events);
        newClubData.put("members", members);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RestSingleton.getInstance(view.getContext()).getUrl() + "createClub", newClubData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ClubFrag frag = new ClubFrag();
                        Bundle data = new Bundle();
                        try {
                            data.putString("clubID", response.getString("_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        frag.setArguments(data);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error connecting", String.valueOf(error));
            }
        });

        RestSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
