
package com.example.cs4532.umdalive.fragments.edit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.UserSingleton;
import com.example.cs4532.umdalive.fragments.base.ProfileFrag;

import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
public class EditProfileFrag extends Fragment {
=======
/**
 * @author Ross Schultz
 *
 * 4/26/2018
 *
 * Class that creates the Edit Profile page
 */
public class EditProfileFrag extends Fragment implements View.OnClickListener{
>>>>>>> d3a9e8bb8742267486294a21a384a9b8c8a0523e

    View view;

    //Layout Components
    private ImageView image;
    private TextView name;
    private EditText major;
    private EditText about;
    private Button save;

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

        loadProfileImage();

        name.setText(UserSingleton.getInstance().getName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (major.getText().length() > 0 && about.getText().length() > 0) {
                    try {
                        editUser();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Return View
        return view;
    }

<<<<<<< HEAD
    private void getLayoutComponents() {
        image = view.findViewById(R.id.editProfileImage);
        name = view.findViewById(R.id.editProfileName);
        major = view.findViewById(R.id.editProfileMajor);
        about = view.findViewById(R.id.editProfileAbout);
        save = view.findViewById(R.id.editProfileSave);
    }

    private void loadProfileImage () {
        if (UserSingleton.getInstance().getProfileUrl() != null) {
            Glide.with(this)
                    .load(UserSingleton.getInstance().getProfileUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);
        } else {
            Glide.with(this)
                    .load("https://images.homedepot-static.com/productImages/42613c1a-7427-4557-ada8-ba2a17cca381/svn/gorilla-carts-yard-carts-gormp-12-64_1000.jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);


    private void editUser() throws JSONException {
        JSONObject newUserData = new JSONObject();
        newUserData.put("name", UserSingleton.getInstance().getName());
        newUserData.put("email", UserSingleton.getInstance().getEmail());
        newUserData.put("major", major.getText());
        newUserData.put("description", about.getText());
        newUserData.put("profilePic", UserSingleton.getInstance().getProfileUrl());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RestSingleton.getInstance(view.getContext()).getUrl() + "editUser", newUserData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProfileFrag frag = new ProfileFrag();
                        Bundle data = new Bundle();
                        data.putString("userID", UserSingleton.getInstance().getUserID());
                        frag.setArguments(data);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error connecting", String.valueOf(error));
            }
        });
<<<<<<< HEAD
=======
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
>>>>>>> d3a9e8bb8742267486294a21a384a9b8c8a0523e

        RestSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
<<<<<<< HEAD
}
=======

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
>>>>>>> d3a9e8bb8742267486294a21a384a9b8c8a0523e
