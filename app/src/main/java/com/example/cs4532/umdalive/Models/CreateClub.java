package com.example.cs4532.umdalive.Models;

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
    public static String makeClub(String clubName, String userName, String keyWords, String ownerEmail, String description) {
        ClubInformationModel newClub = new ClubInformationModel(clubName, userName, keyWords, ownerEmail, description);

        return newClub.jsonStringify();
    }
}
