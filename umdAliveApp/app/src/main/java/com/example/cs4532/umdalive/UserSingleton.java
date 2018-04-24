package com.example.cs4532.umdalive;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserSingleton {

    private String userID;
    private static UserSingleton instance;

    private UserSingleton (){}

    public static synchronized UserSingleton getInstance() {
        if (instance == null) {
            return new UserSingleton();
        } else {
            return instance;
        }
    }

    public void setUserID(GoogleSignInAccount account) {
        userID = account.getId();
    }

    public String getUserID() {
        return userID;
    }
}
