package com.example.cs4532.umdalive;

import android.content.Context;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RestCalls {

    RequestQueue queue;

    public RestCalls(Context context) {
        queue = Volley.newRequestQueue(context);
    }

/*

GetUser(userID)

GetClub(clubID)

JoinClub(userID,clubID)

createClub(clubObject)

GetEvent(eventId)

CreateEvent(eventId, eventDate, eventDescription)


 */
}
