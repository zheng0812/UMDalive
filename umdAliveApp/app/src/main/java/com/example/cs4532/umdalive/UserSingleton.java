package com.example.cs4532.umdalive;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserSingleton {

    private GoogleSignInAccount account;
    private static UserSingleton instance;

    private UserSingleton (){}

    public static synchronized UserSingleton getInstance() {
        if (instance == null) {
            return new UserSingleton();
        } else {
            return instance;
        }
    }

    public void setUserID(GoogleSignInAccount a) {
        account = a;
    }

    public String getUserID () {
        return account.getId();
    }

    public String getName () {
        return account.getDisplayName();
    }

    public String getEmail() { return account.getEmail(); }

    public String getProfileUrl() {
        return account.getPhotoUrl().toString();
    }
}
