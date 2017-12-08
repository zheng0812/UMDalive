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
import android.widget.Toast;

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
     *makes yes and no buttons visible to confirm delete
     *
     * @param view passing view
     */
    public void onClickDeleteClub(View view) {
        findViewById(R.id.noButton).setVisibility(View.VISIBLE);
        findViewById(R.id.yesButton).setVisibility(View.VISIBLE);
        findViewById(R.id.warningPrompt).setVisibility(View.VISIBLE);
    }

    /**
     * makes yes and no buttons invisible when user decides not to delete
     *
     * @param view passing view
     */
    public void onClickNoDelete(View view){
        findViewById(R.id.noButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.yesButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.warningPrompt).setVisibility(View.INVISIBLE);
    }

    /**
     * deletes a club from the collection of clubs
     *
     * @param view passing view
     */
    public void onClickYesDelete(View view){
        String clubName = ((EditText)findViewById(R.id.editText)).getText().toString();
        if (!presenter.restDelete("deleteClub", clubName))
        {
            Toast.makeText(getApplicationContext(), "Club not found", Toast.LENGTH_LONG).show();

        }
        else Toast.makeText(getApplicationContext(), "Club successfully deleted", Toast.LENGTH_LONG).show();
        findViewById(R.id.noButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.yesButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.warningPrompt).setVisibility(View.INVISIBLE);
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