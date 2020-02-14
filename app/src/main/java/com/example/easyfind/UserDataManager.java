package com.example.easyfind;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserDataManager {

        public static final String LOGGED_IN_PREF = "logged_in_status";
        public static final String USER_IN_PREF = "user_name";
        public static final String PASS_IN_PREF = "password";

        static SharedPreferences getPreferences(Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }

        public static void setLoggedIn(Context context, boolean loggedIn) {
            SharedPreferences.Editor editor = getPreferences(context).edit();
            editor.putBoolean(LOGGED_IN_PREF, loggedIn);
            editor.apply();
        }

        public static boolean getLoggedStatus(Context context) {
            return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
        }

        public static void setUserName(Context context, String userName) {
            SharedPreferences.Editor editor = getPreferences(context).edit();
            editor.putString(USER_IN_PREF, userName);
            editor.apply();
        }

        public static String getUserName(Context context) {
            return getPreferences(context).getString(USER_IN_PREF, "null");
        }

        public static void setPassword(Context context, String password) {
            SharedPreferences.Editor editor = getPreferences(context).edit();
            editor.putString(PASS_IN_PREF, password);
            editor.apply();
        }

        public static String getPassword(Context context) {
            return getPreferences(context).getString(PASS_IN_PREF, "null");
        }
}
