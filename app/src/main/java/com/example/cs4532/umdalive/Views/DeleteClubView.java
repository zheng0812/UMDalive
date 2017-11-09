package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

/**
 * Created by meggi on 11/9/2017.
 */

public class DeleteClubView extends AppCompatActivity {

    private Object keywordItem = new Object();
    private Presenter presenter;
    private EditText newName;
    private EditText admin;
    private EditText description;
    private EditText ownerEmail;
    private TextView invalidInput;
    private String errorMessage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this);
        setContentView(R.layout.delete_club_view);
    }

    /**
     *
     *
     * @param view passing view
     */
    public void onClickMakeClub(View view) {
        Intent intent = new Intent(this, MainView.class);
        invalidInput = (TextView) findViewById(R.id.invalid_input);
        newName = (EditText) findViewById(R.id.name_title_enter);
        admin = (EditText) findViewById(R.id.admin_of_club);
        ownerEmail = (EditText) findViewById(R.id.admin_email);
        description = (EditText) findViewById(R.id.description_of_club);

        if (!checkStrings()) {
            String jsonString = presenter.makeClub(newName.getText().toString(),
                    (String) keywordItem, ownerEmail.getText().toString(), description.getText().toString());
            startActivity(intent);
            presenter.restPut("putNewClub", jsonString);
        } else invalidInput.setText(errorMessage);
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
        if (admin.getText().toString().matches("") || presenter.isClubInfoValid(admin.getText().toString())) {
            errorMessage = "You must enter a valid admin name.";
            isError = true;
        }
        if (description.getText().toString().matches("") || presenter.isClubInfoValid(description.getText().toString())) {
            errorMessage = "You must enter a valid description.";
            isError = true;
        }
        return isError;
    }
}