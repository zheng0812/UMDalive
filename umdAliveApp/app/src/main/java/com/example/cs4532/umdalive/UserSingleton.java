package com.example.cs4532.umdalive;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserSingleton {

    private GoogleSignInAccount account;

    private static final UserSingleton instance = new UserSingleton();

    private UserSingleton (){}

    public static synchronized UserSingleton getInstance() {
        return instance;
    }

    public void setAccount(GoogleSignInAccount a) {
        instance.account = a;
    }

    public String getUserID () {
        return instance.account.getId();
    }

    public String getName () {
        return instance.account.getDisplayName();
    }

    public String getEmail() {
        return instance.account.getEmail();
    }

    public String getProfileUrl() {
        if (instance.account.getPhotoUrl() != null) {
            return instance.account.getPhotoUrl().toString();
        } else {
            return null;
        }
    }
}
