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

        //for the current club you are editing
        private String clubName;
        private String description;
        private String ownerEmail;
        private String clubOwner;

        //for use of the editableview.xml
        private EditText clubNameEditable;
        private EditText descriptionEditable;
        private TextView ownerEmailSetText;
        private TextView clubOwnerSetText;
        private Button saveButton;

        //for the new club when saving edits
        private EditText newName;
        private EditText newDescription;
        private TextView invalidInput;
        private String errorMessage;


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
         * sets up view for editing the club
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
            ownerEmailSetText = (TextView) findViewById(R.id.edit_email);
            clubOwnerSetText = (TextView) findViewById(R.id.edit_owner);
            invalidInput = (TextView) findViewById(R.id.edit_invalid_input);

            //clubOwners can edit clubName and description
            clubNameEditable.setText(clubName);
            descriptionEditable.setText(description);

            //these are not editable, only administrators will change these
            ownerEmailSetText.setText(ownerEmail);
            clubOwnerSetText.setText(clubOwner);
        }

        /**
        * switches for a view for deleting posts
        *
        * @param view
        */
        public void deletePost(View view)
        {
            Intent intent = new Intent (this, DeletingPostView.class);
            intent.putExtra("NAME_OF_CLUB", clubName);
            startActivity(intent);
        }

    /**
     * onClick function for the Save button in the editable view
     *
     * This function stores the edited information entered by the
     * user, deletes the old club, and creates a new club with
     * the new information.
     *
     * @param view
     */
    public void clickToSaveClubInfo(View view){
            Intent intent = new Intent (this, DisplayClubOwnerView.class);

            invalidInput = (TextView) findViewById(R.id.edit_invalid_input);
            newName = (EditText) findViewById(R.id.edit_title);
            newDescription = (EditText) findViewById(R.id.edit_description);


            presenter.restDelete("deleteClub", clubName);

            if (!checkStrings()) {
                String jsonString = presenter.makeClub(newName.getText().toString(),
                        (String) keywordItem, ownerEmailSetText.getText().toString(), newDescription.getText().toString());
                startActivity(intent);
                presenter.restPut("putNewClub", jsonString);
            } else invalidInput.setText(errorMessage);


            intent.putExtra("NAME_OF_CLUB", newName.getText().toString());
            startActivity(intent);
        }

    /**
     * Checks if strings are invalid
     *
     * @return false if invalid input
     */
    private boolean checkStrings() {
        boolean isError = false;

        errorMessage = "";
        if (newName.getText().toString().matches("") || presenter.isClubNameValid(newName.getText().toString())) {
            errorMessage = "You must enter a valid club name";
            isError = true;
        }
        if (clubOwnerSetText.getText().toString().matches("") || presenter.isClubInfoValid(clubOwnerSetText.getText().toString())) {
            errorMessage = "You must enter a valid admin name.";
            isError = true;
        }
        if (newDescription.getText().toString().matches("") || presenter.isClubInfoValid(newDescription.getText().toString())) {
            errorMessage = "You must enter a valid description.";
            isError = true;
        }
        return isError;
    }
}
