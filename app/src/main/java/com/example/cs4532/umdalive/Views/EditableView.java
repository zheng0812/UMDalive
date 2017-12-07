package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by meggi on 11/5/2017.
 */


public class EditableView extends AppCompatActivity{

    public static final String CLUB_NAME = "com.example.kevin.umdalive.MESSAGE";

        private Presenter presenter;
        private String clubName;
        private String description;
        private String keywords;
        private String ownerEmail;
        private String clubOwner;
        private EditText clubNameEditable;
        private EditText descriptionEditable;
        private EditText keywordSetText;
        private TextView ownerEmailSetText;
        private TextView clubOwnerSetText;
        private TextView invalidInput;
        private Button saveButton;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.editable_view);
            presenter = new Presenter(this);
            saveButton = (Button) findViewById(R.id.save_button);
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
/**
                description = getIntent().getStringExtra("DESCRIPTION_OF_CLUB");
                keywords = getIntent().getStringExtra("KEYWORDS_OF_CLUB");
                clubOwner = getIntent().getStringExtra("ADMINISTRATOR_OF_CLUB");
                ownerEmail = getIntent().getStringExtra("OWNER_EMAIL_OF_CLUB");
*/
                keywords= clubObject.getJSONObject(clubName).getString("keywords");
                description = clubObject.getJSONObject(clubName).getString("description");
                //description = clubObject.get("description").toString();
                //keywords = clubObject.get("keywords").toString();
                //clubOwner = clubObject.get("username").toString();
                //ownerEmail = clubObject.get("ownerEmail").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            clubNameEditable = (EditText) findViewById(R.id.edit_title);
            descriptionEditable = (EditText) findViewById(R.id.edit_description);
           // keywordSetText = (EditText) findViewById(R.id.);
            ownerEmailSetText = (EditText) findViewById(R.id.edit_email);
            clubOwnerSetText = (EditText) findViewById(R.id.edit_owner);
            invalidInput = (TextView) findViewById(R.id.edit_invalid_input);

            clubNameEditable.setText(clubName, TextView.BufferType.EDITABLE);
            descriptionEditable.append(description);
//            descriptionEditable.setText(description, TextView.BufferType.EDITABLE);
            //keywordSetText.setText(keywords, TextView.BufferType.EDITABLE);

            //feed these ones in like the createClub
            ownerEmailSetText.setText(ownerEmail, TextView.BufferType.NORMAL); //should not be editable
            clubOwnerSetText.setText(clubOwner, TextView.BufferType.NORMAL); //should not be editable
        }

        public void deletePost(View view)
        {
            Intent intent = new Intent (this, DeletingPostView.class);
            intent.putExtra("NAME_OF_CLUB", clubName);
            startActivity(intent);
        }


        public void clickToSave(View view){
            Intent intent = new Intent (this, DisplayClubOwnerView.class);
            //update edit text views before switching views? or after?

            //intent.putExtra("NAME_OF_CLUB", clubNameEditable);

            startActivity(intent);
        }
}
