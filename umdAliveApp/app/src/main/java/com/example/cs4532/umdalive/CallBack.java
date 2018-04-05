package com.example.cs4532.umdalive;

import org.json.JSONException;
import org.json.JSONObject;

public interface CallBack {
    void callBack(JSONObject serverResponse) throws JSONException;
}
