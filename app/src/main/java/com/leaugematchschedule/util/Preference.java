package com.leaugematchschedule.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.leaugematchschedule.LeagueMatchApp;
import com.leaugematchschedule.R;


/**
 * <p>
 * Purpose of this class is to save data in preference and retrieve values from preference throughout the lifecycle of application
 * This class is hold methods for storing and retrieving values from preference.
 * </p>
 */
public class Preference {
    public static final String PREFERENCE_LANG_ID = "LANG_ID";
    private static Preference preference;
    public final String PREFERENCE_USER_IS_LOGIN = "USER_IS_LOGIN";

    public final String PREFERENCE_USER_ID = "USER_ID";
    public final String PREFERENCE_IS_GUEST_SIGN_IN = "IS_GUEST_SIGN_IN";
    public final String PREFERENCE_NAME = "NAME";
    public final String PREFERENCE_SURNAME = "SURNAME";
    public final String PREFERENCE_EMAIL_ID = "EMAIL_ID";
    public final String PREFERENCE_PASSWORD = "PASSWORD";
    public final String PREFERENCE_USER_IMAGE_URL = "USER_IMAGE_URL";
    public final String PREFERENCE_REMEMBER_ME = "REMEMBER_ME";

    public SharedPreferences sharedPreferences;

    private Preference() {
        sharedPreferences = LeagueMatchApp.getInstance().getSharedPreferences(LeagueMatchApp.getInstance().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * @return the {@link SharedPreferences} object that will be used to save values in the application preference
     */
    public static Preference getInstance() {
        if (preference == null) {
            preference = new Preference();
        }
        return preference;
    }

    /**
     * Returns the userId from the Shared Preference file
     *
     * @return userId
     */
    public String getUserId() {
        return sharedPreferences.getString(PREFERENCE_USER_ID, "");
    }

    /**
     * Stores the userId into Shared Preference file
     */
    public void setUserId(final String userId) {
        sharedPreferences.edit().putString(PREFERENCE_USER_ID, userId).apply();
    }

    /**
     * Stores the {@link String} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link String} parameter for the value to be stored in preference
     */
    public void setData(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * Stores the {@link String} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link Boolean} parameter for the value to be stored in preference
     */
    public void setData(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getData(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getData(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * clearAllPreferenceData : it will clear all data from preference
     */
    public void clearAllPreferenceData() {
        String email = getData(PREFERENCE_EMAIL_ID, "");
        String password = getData(PREFERENCE_PASSWORD, "");
        boolean rememberMe = getData(PREFERENCE_REMEMBER_ME, false);

        sharedPreferences.edit().clear().apply();

        setData(PREFERENCE_EMAIL_ID, email);
        setData(PREFERENCE_PASSWORD, password);
        setData(PREFERENCE_REMEMBER_ME, rememberMe);
    }
}