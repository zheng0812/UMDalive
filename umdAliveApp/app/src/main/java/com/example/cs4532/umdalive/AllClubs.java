package com.example.cs4532.umdalive;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class AllClubs implements View.OnClickListener{

    private Context context;
    private Activity activity;
    private CardView clubList;

    public AllClubs (Context c, Activity a){
        context = c;
        activity = a;

        clubList = activity.findViewById(R.id.ClubList);
    }

    public void Generate () {

        Button test = new Button(context);

        clubList.addView(test);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();


    }
}
