package com.example.cs4532.umdalive.fragments.create;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    private EditText ClubURL;
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
                if (ClubURL.getText() != null && ClubName.getText() != null && ClubDescription.getText() != null) {
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
        ClubURL = view.findViewById(R.id.clubURL);
        ClubName = view.findViewById(R.id.clubName);
        ClubDescription = view.findViewById(R.id.clubDescription);
        save = view.findViewById(R.id.save);
    }

    private void createClub() throws JSONException {
        JSONObject club = new JSONObject();
        club.put("name", ClubName.getText());
        club.put("description", ClubDescription.getText());
        club.put("profilePic", ClubURL.getText());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RestSingleton.getInstance(view.getContext()).getUrl() + "createClub", club,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ClubFrag frag = new ClubFrag();
                        Bundle data = new Bundle();
                        data.putString("clubID", );
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
