package com.example.cs4532.umdalive.fragments.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.fragments.edit.EditClubFrag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Requires argument with key of clubID to be passed into it before it is added to the frame layout
 */

public class ClubFrag extends Fragment{

    //View
    View view;

    //Layout Components
    private TextView clubName;
    private TextView clubDescription;
    private LinearLayout members;
    private LinearLayout eventsList;
    private Button LeaveJoin;
    private FloatingActionButton editClub;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.club_layout, container, false);

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


    private void getLayoutComponents() {
        clubName = (TextView) view.findViewById(R.id.ClubNameView);
        clubDescription = (TextView) view.findViewById(R.id.DescriptionView);
        members = (LinearLayout) view.findViewById(R.id.MembersLayout);
        eventsList = (LinearLayout) view.findViewById(R.id.EventsLayout);
        LeaveJoin = (Button) view.findViewById(R.id.ClubJoin);
        LeaveJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestSingleton rs = RestSingleton.getInstance(view.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, rs.getUrl() + "getClub/" + getArguments().getString("clubID"),
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
                rs.addToRequestQueue(stringRequest);
            }
        });
        editClub = (FloatingActionButton) view.findViewById(R.id.EditClub);
        editClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditClubFrag frag = new EditClubFrag();
                Bundle data = new Bundle();
                data.putString("clubID", clubName.getTag().toString());
                frag.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
            }
        });
    }

    private void updateUI(JSONObject res) throws JSONException{
        //String UserName = UserSingleton.getInstance().getName();
        //Log.d("Name Test", UserName);
        getActivity().findViewById(R.id.PageLoading).setVisibility(View.GONE);
        clubName.setText(res.getString("name"));
        clubName.setTag(res.getString("_id"));
        clubDescription.setText(res.getString("description"));
        JSONObject memberJson = res.getJSONObject("members");
        JSONArray regulars = memberJson.getJSONArray("regular");
        JSONArray admins = memberJson.getJSONArray("admin");
        JSONArray events = res.getJSONArray("events");
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
            member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String TAG = (String) v.getTag();
                    ProfileFrag frag = new ProfileFrag();
                    Bundle data = new Bundle();
                    data.putString("userID", TAG);
                    frag.setArguments(data);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                }
            });
            hl.addView(member);
            hl.addView(admin);
            members.addView(hl);
        }
        for (int i=0;i<regulars.length();i++){
            String name = regulars.getJSONObject(i).getString("name");
            TextView member = new TextView(view.getContext());
            member.setText(name);
            member.setTextSize(16);
            member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String TAG = (String) v.getTag();
                    ProfileFrag frag = new ProfileFrag();
                    Bundle data = new Bundle();
                    data.putString("userID", TAG);
                    frag.setArguments(data);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                }
            });
            members.addView(member);
        }
        if(events != null) {
            eventsList.setVisibility(View.VISIBLE);
            for (int i = 0; i < events.length(); i++) {
                LinearLayout hl = new LinearLayout(view.getContext());
                hl.setOrientation(LinearLayout.HORIZONTAL);
                String name = events.getJSONObject(i).getString("name");
                String day = events.getJSONObject(i).getString("date");
                String id = events.getJSONObject(i).getString("eventID").toString();
                TextView event = new TextView(view.getContext());
                TextView date = new TextView(view.getContext());
                date.setText(day);
                date.setTextSize(16);
                date.setGravity(Gravity.RIGHT);
                date.setWidth(members.getWidth()/2);
                event.setText(name);
                event.setTextSize(16);
                event.setWidth(members.getWidth()/2);
                event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String TAG = (String) v.getTag();
                        EventFrag frag = new EventFrag();
                        Bundle data = new Bundle();
                        data.putString("eventID", TAG);
                        frag.setArguments(data);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                    }
                });
                event.setTag(id);
                hl.addView(event);
                hl.addView(date);
                eventsList.addView(hl);
            }
        }
    }
}