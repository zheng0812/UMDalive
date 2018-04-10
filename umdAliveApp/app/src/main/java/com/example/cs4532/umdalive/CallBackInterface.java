package com.example.cs4532.umdalive;

import org.json.JSONException;
import org.json.JSONObject;

public interface CallBackInterface {
    void callBack(JSONObject serverResponse) throws JSONException;
    void showProfile(String userID);
    void showClub(String clubID);
}

abstract class CallBack implements CallBackInterface {
    @Override
    public void callBack(JSONObject serverResposnse) throws JSONException {};
    @Override
    public void showProfile(String userID) {}
    @Override
    public void showClub(String clubID) {}

}



