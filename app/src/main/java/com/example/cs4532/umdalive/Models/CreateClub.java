package com.example.cs4532.umdalive.Models;



import com.example.cs4532.umdalive.Presenters.Presenter;

/**
 * This is the model class for CreateClubView
 */

public class CreateClub {

    /**
     * @param clubName    name of club
     * @param userName    user creating club
     * @param keyWords    keywords to id club
     * @param ownerEmail  club owner email
     * @param description of club
     * @return String representation of new club
     */
    public static String makeClub(String clubName, String userName, String ownerEmail, String keyWords, String description) {
        Presenter presenter = new Presenter();
        ClubInformationModel newClub = new ClubInformationModel(clubName, userName, ownerEmail, keyWords, description);
        //presenter.getMainUser((presenter.restGet("getUserEmail", presenter.getThisUser()))).setUserType(clubName);
        return newClub.jsonStringify();
    }
}
