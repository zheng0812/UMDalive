package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

        private Object keywordItem = new Object();
        private Presenter presenter;
        private String clubName;
        private String description;
        private String ownerEmail;
        private String clubOwner;
        private EditText clubNameEditable;
        private EditText descriptionEditable;
        private TextView ownerEmailSetText;
        private TextView clubOwnerSetText;
        private TextView invalidInput;
        private Button saveButton;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.editable_view);

            Spinner spinner = (Spinner) findViewById(R.id.keywordSelect); // Create an ArrayAdapter using the string array and a default spinner layout
            //keyword_list is the list of all the club categories. We should probably expand this at some point and add on "other" option.
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.keyword_list, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            presenter = new Presenter(this);
            saveButton = (Button) findViewById(R.id.save_button);
            setView();

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    keywordItem = parent.getItemAtPosition(pos);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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

                description = clubObject.get("description").toString();
                clubOwner = clubObject.get("clubOwner").toString();
                ownerEmail = clubObject.get("ownerEmail").toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            clubNameEditable = (EditText) findViewById(R.id.edit_title);
            descriptionEditable = (EditText) findViewById(R.id.edit_description);
            //keywordSetText = (Spinner) findViewById(R.id.keywordChooser);
            ownerEmailSetText = (TextView) findViewById(R.id.edit_email);
            clubOwnerSetText = (TextView) findViewById(R.id.edit_owner);
            invalidInput = (TextView) findViewById(R.id.edit_invalid_input);

            clubNameEditable.setText(clubName);
            descriptionEditable.setText(description);

            //feed these ones in like the createClub
            ownerEmailSetText.setText(ownerEmail); //should not be editable
            clubOwnerSetText.setText(clubOwner); //should not be editable
        }

        public void deletePost(View view)
        {
            Intent intent = new Intent (this, DeletingPostView.class);
            intent.putExtra("NAME_OF_CLUB", clubName);
            startActivity(intent);
        }


        public void clickToSaveClubInfo(View view){
            Intent intent = new Intent (this, DisplayClubOwnerView.class);
            //update edit text views before switching views? or after?

            //intent.putExtra("NAME_OF_CLUB", clubNameEditable);

            startActivity(intent);
        }
}
