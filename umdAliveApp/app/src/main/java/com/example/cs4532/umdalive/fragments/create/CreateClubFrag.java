package com.example.cs4532.umdalive.fragments.create;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs4532.umdalive.R;


public class CreateClubFrag extends Fragment {
    View view;

    private EditText ClubURL;
    private EditText ClubName;
    private EditText ClubDescription;
    private Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.create_club_layout, container, false);


        return view;
    }


}
