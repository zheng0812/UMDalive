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
    public void onClickDeleteClub(View view) {
        String clubName = ((EditText)findViewById(R.id.editText)).getText().toString();
        //((EditText) findViewById(R.id.editText)).setText("Trying to delete: " + clubName);
        presenter.deleteClub(clubName);
        //presenter.restDelete("clubName", clubName);
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