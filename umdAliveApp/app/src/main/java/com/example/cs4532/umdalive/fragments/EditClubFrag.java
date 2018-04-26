package com.example.cs4532.umdalive.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Josh on 4/25/2018.
 */

public class EditClubFrag extends Fragment implements View.OnClickListener {
    //View
    View view;

    //Layout Components
    private TextView EditingClub;
    private EditText NewClubName;
    private EditText NewClubDescription;
    private Button SaveButton;

    private JSONObject clubData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.edit_club_layou, container, false);

        //Get Layout Components
        getLayoutComponents();

        //Use Volley Singleton to Update Page UI
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
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
        if(NewClubName.getText().toString().trim().length()!=0){
            try {
                clubData.put("name",NewClubName.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(NewClubDescription.getText().toString().trim().length()!=0){
            try {
                clubData.put("description",NewClubDescription.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, restSingleton.getUrl() + "editClub/", clubData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error connecting", String.valueOf(error));
            }
        });
        restSingleton.addToRequestQueue(jsonObjectRequest);
        ClubFrag frag = new ClubFrag();
        Bundle data = new Bundle();
        data.putString("clubID", EditingClub.getTag().toString());
        frag.setArguments(data);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
    }

    private void getLayoutComponents() {
        EditingClub = view.findViewById(R.id.ClubEditing);
        NewClubName = view.findViewById(R.id.NewClubName);
        NewClubDescription = view.findViewById(R.id.NewClubDescription);
        SaveButton = view.findViewById(R.id.SaveClub);
        SaveButton.setOnClickListener(this);
    }

    private void updateUI(JSONObject res) throws JSONException {
        EditingClub.setText("Editing Club:\n" + res.getString("name"));
        EditingClub.setTag(res.getString("_id"));
        NewClubName.setText(res.getString("name"));
        NewClubDescription.setText(res.getString("description"));
        clubData = res;

    }
}
