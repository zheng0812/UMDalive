package com.example.cs4532.umdalive.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

/**
 * Created by meggi on 11/30/2017.
 */

public class  DeletingPostView extends AppCompatActivity {

    private Presenter presenter;
    EditText myTitle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this);
        setContentView(R.layout.deleting_post);
    }

    /**
     * @param view passing view
     */
    public void onClickDeletePost(View view) {
        findViewById(R.id.noButton2).setVisibility(View.VISIBLE);
        findViewById(R.id.yesButton2).setVisibility(View.VISIBLE);
        findViewById(R.id.warningPrompt2).setVisibility(View.VISIBLE);
    }

    public void onClickNoDeletePost(View view) {
        findViewById(R.id.noButton2).setVisibility(View.INVISIBLE);
        findViewById(R.id.yesButton2).setVisibility(View.INVISIBLE);
        findViewById(R.id.warningPrompt2).setVisibility(View.INVISIBLE);
    }

    public void onClickYesDeletePost(View view) {
        //why is title getting nothing?
        myTitle = ((EditText) findViewById(R.id.edit_title2));
        String title = myTitle.getText().toString();

        if (!presenter.checkIfClubOwner(title)) {
            Toast.makeText(getApplicationContext(), "I'm sorry, you are not the owner of that club", Toast.LENGTH_LONG).show();
        }else if (!presenter.restDelete("deletePost", title) ) {
            Toast.makeText(getApplicationContext(), "Post not found", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Option not currently available", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Post successfully deleted", Toast.LENGTH_LONG).show();
        }
        findViewById(R.id.noButton2).setVisibility(View.INVISIBLE);
        findViewById(R.id.yesButton2).setVisibility(View.INVISIBLE);
        findViewById(R.id.warningPrompt2).setVisibility(View.INVISIBLE);

    }
}