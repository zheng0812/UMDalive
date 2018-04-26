
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

/**
 * @author Ross Schultz
 *
 * 4/26/2018
 *
 * Class that creates the Edit Profile page
 */
public class EditProfileFrag extends Fragment {

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
     *
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

    /**
     * Gets the layout components from edit_profile_layout.xml
     *
     * @return nothing
     */
    private void getLayoutComponents() {
        image = view.findViewById(R.id.editProfileImage);
        name = view.findViewById(R.id.editProfileName);
        major = view.findViewById(R.id.editProfileMajor);
        about = view.findViewById(R.id.editProfileAbout);
        save = view.findViewById(R.id.editProfileSave);
    }

    private void loadProfileImage() {
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

        }
    }

    private void editUser() throws JSONException {
    }


}