
package com.example.cs4532.umdalive.fragments.edit;

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
import com.example.cs4532.umdalive.fragments.base.ProfileFrag;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ross Schultz
 *
 * 4/26/2018
 *
 * Class that creates the Edit Profile page
 */
public class EditProfileFrag extends Fragment implements View.OnClickListener{

    View view;

    //Layout Components
    private TextView EditingProfile;
    private EditText majorEditText;
    private EditText aboutEditText;
    private Button saveButton;

    private JSONObject userData;

    /**
     * Creates the edit profile  view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view The view of the edit profile view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.edit_profile_layout, container, false);

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

    /**
     * Allows the user to click ont he textViews that can be edited
     * @param view The textView clicked
     * @return nothing
     */
    @Override
    public void onClick(View view) {
        if(majorEditText.getText().toString().trim().length()!=0){
            try {
                userData.put("major",majorEditText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(aboutEditText.getText().toString().trim().length()!=0){
            try {
                userData.put("about",aboutEditText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, restSingleton.getUrl() + "editUser/", userData,
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
        ProfileFrag frag = new ProfileFrag();
        Bundle data = new Bundle();
        data.putString("userID", EditingProfile.getTag().toString());
        frag.setArguments(data);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
    }

    /**
     * Gets the layout components from edit_profile_layout.xml
     * @return nothing
     */
    private void getLayoutComponents() {
        EditingProfile = (TextView) view.findViewById(R.id.ProfileEditing);
        majorEditText = (EditText) view.findViewById(R.id.majorEdit);
        aboutEditText = (EditText) view.findViewById(R.id.aboutMeEdit);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

    }

    /**
     * Adds the textViews that can be edited, which may include
     * previously added information
     * @param res The response from the database
     * @throws JSONException Error in JSON processing
     * @see JSONException
     */
    private void updateUI(JSONObject res) throws JSONException {
        EditingProfile.setText("Editing Profile:\n" + res.getString("name"));
        EditingProfile.setTag(res.getString("_id"));
        majorEditText.setText(res.getString("major"));
        aboutEditText.setText(res.getString("about"));
        userData = res;
    }

}