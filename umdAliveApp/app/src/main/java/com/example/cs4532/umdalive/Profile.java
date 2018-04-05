package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile implements View.OnClickListener{

    private Activity activity;

    private Context context;

    private TextView profileName;

    private TextView profileMajor;

    private RestCalls profileRest;

    public Profile(Activity a, Context c) {
        activity = a;
        context = c;

        profileName = (TextView) activity.findViewById(R.id.profileName);
        profileMajor = (TextView) activity.findViewById(R.id.profileMajor);
    }

        public void buildPage (String profileId)  throws JSONException {
            profileRest.getUser(profileId, new CallBack() {
                @Override
                public void callBack(JSONObject serverResponse) throws JSONException {
                    updateUI(serverResponse);
                }
            });
    }

    private void updateUI(JSONObject profileData) throws JSONException{

        profileName.setText(profileData.getString("name"));
        profileMajor.setText(profileData.getString("description"));

    }

    @Override
    public void onClick(View v) {

    }
}


