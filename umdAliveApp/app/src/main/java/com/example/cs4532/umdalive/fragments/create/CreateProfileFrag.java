package com.example.cs4532.umdalive.fragments.create;

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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.UserSingleton;
import com.example.cs4532.umdalive.fragments.base.ProfileFrag;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProfileFrag extends Fragment {

    //View
    View view;

    private ImageView profileImage;
    private TextView name;
    private EditText major;
    private EditText about;
    private Button save;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.create_profile_layout, container, false);

        getActivity().findViewById(R.id.PageLoading).setVisibility(View.GONE);

        getLayoutComponents();

        loadProfileImage();

        name.setText(UserSingleton.getInstance().getName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (major.getText().length() > 0 && about.getText().length() > 0) {
                    try {
                        createUser();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    //Sets the Text views of the profile layout
    private void getLayoutComponents() {
        profileImage = view.findViewById(R.id.profileImage);
        name = view.findViewById(R.id.userName);
        major = view.findViewById(R.id.userMajor);
        about = view.findViewById(R.id.userAbout);
        save = view.findViewById(R.id.save);
    }

    private void loadProfileImage () {
        if (UserSingleton.getInstance().getProfileUrl() != null) {
            Glide.with(this)
                    .load(UserSingleton.getInstance().getProfileUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
        } else {
            Glide.with(this)
                    .load("https://images.homedepot-static.com/productImages/42613c1a-7427-4557-ada8-ba2a17cca381/svn/gorilla-carts-yard-carts-gormp-12-64_1000.jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
        }
    }

    private void createUser() throws JSONException {
        JSONObject user = new JSONObject();
        user.put("name", UserSingleton.getInstance().getName());
        user.put("email", UserSingleton.getInstance().getEmail());
        user.put("major", major.getText());
        user.put("userID", UserSingleton.getInstance().getUserID());
        user.put("description", about.getText());
        user.put("profilePic", UserSingleton.getInstance().getProfileUrl());

        System.out.println(user);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RestSingleton.getInstance(view.getContext()).getUrl() + "createUser", user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Jdfhlaskdjh falksdjfha lksdjfha lksjdhflkja hefioauehf lkja sdhflkajshdfl kjaheiuahysd kvnbkjzhfldkjahf ieuasdklfjh alkjdhf ieouhf adbf lkajhdflkjyeaiou absdklfjh adkhaoi euh alkdjhflkjschve");
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

        RestSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
