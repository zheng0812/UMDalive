package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meggi on 11/7/2017.
 */

public class DisplayClubOwnerView extends AppCompatActivity {

        private Presenter presenter;
        private String clubName;
        private String description;
        private String keywords;
        private String ownerEmail;
        private String administrator;
        private TextView clubNameSetText;
        private TextView descriptionSetText;
        private TextView keywordSetText;
        private TextView administratorSetText;

    public static final String CLUB_NAME2 = "com.example.kevin.umdalive.MESSAGE2";
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.display_club_owner_view);
            presenter = new Presenter(this);
            setView();
        }

        /**
         * sets up view
         */
        private void setView() {
            try {
                clubName = getIntent().getStringExtra("NAME_OF_CLUB");
                String jsonResponse = presenter.restGet("getClub", clubName);
                Log.d("DisplayClub response: ", jsonResponse);
                JSONObject clubObject = new JSONObject(jsonResponse);
                //description = clubObject.get("description").toString();
                //keywords = clubObject.get("keywords").toString();
                //ownerEmail = clubObject.get("ownerEmail").toString();
                //administrator = clubObject.get("clubOwner").toString();

               // keywords= clubObject.getJSONObject(clubName).getString("keywords");
              //  description = clubObject.getJSONObject(clubName).getString("description");
                System.out.println("help me"+description);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            clubNameSetText = (TextView) findViewById(R.id.display_club_owner_name);
            descriptionSetText = (TextView) findViewById(R.id.display_club_owner_description);
            descriptionSetText.setText(description);
            keywordSetText = (TextView) findViewById(R.id.display_clubs_owner_keyword);
            administratorSetText = (TextView) findViewById(R.id.display_club_owner_administrator);

            clubNameSetText.setText(clubName);
            clubNameSetText.setTextSize(45);
            //descriptionSetText.setText(description);
            //keywordSetText.setText(keywords);
            administratorSetText.setText(administrator);
        }

    /**
     * Goes to the editable view
     */
    public void clickToEdit(View view)
        {
            Intent intent = new Intent(this, EditableView.class);
            intent.putExtra("NAME_OF_CLUB", clubName);
           intent.putExtra("DESCRIPTION_OF_CLUB", description);
            intent.putExtra("ADMINISTRATOR_OF_CLUB", administrator);
            intent.putExtra("KEYWORDS_OF_CLUB", keywords);
            intent.putExtra("OWNER_EMAIL_OF_CLUB", ownerEmail);

            startActivity(intent);
        }

    /**
     * Goes back to the home screen
     */
    public void clickOwnerGoHome(View view)
    {
        Intent intent = new Intent (this, MainView.class);
        startActivity(intent);
    }
}

