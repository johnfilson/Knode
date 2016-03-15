package com.fyp.knode.helper;

import com.fyp.knode.KnodeConstants.Constants;

import java.util.prefs.Preferences;

/**
 * Created by Johnny on 10/03/2016.
 */
public class userDescription {


    protected static String [] users;
    protected static String [] Roles;

    public static void Recommender(){

        users = new String[Constants.USER_LIMIT];
        Roles = new String[Constants.USER_ROLES];

        Roles[0] = "Developer";
        Roles[1] = "Tester";
        Roles[2] = "Project Manager";
        Roles[3] = "Recruiter";
        Roles[4] = "Business Analyst";
        Roles[5] = "Frontend-Developer";
        Roles[6] = "UX Designer";
        Roles[7] = "User-researcher";
        Roles[8] = "Software Architect";
        Roles[9] = "Database manager";
        Roles[10] = "Strart-up Entrepreneur";
        Roles[11] = "Researcher";
        Roles[12] = "Scrum master";
        Roles[13] = "Investor";
        Roles[14] = "Business owner";


        for (int i = 0; i < users.length; i ++){
            for (int j =0; j < Roles.length;  j ++){

            }
        }
    }


}
