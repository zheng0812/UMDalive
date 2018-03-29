package com.example.cs4532.umdalive;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class AllClubs implements View.OnClickListener{

    Context context;
    CardView clubList;

    public AllClubs (Context c, CardView cv){
        context = c;
        clubList = cv;
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
