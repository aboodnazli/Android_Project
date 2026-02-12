package com.example.midtermproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserManager {

    private static UserManager instance;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPreferences";
    private static final String USER_KEY = "user_data";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private Gson gson;

    public static class User {
        public String fullName;
        public String email;
        public String phone;
        public String password;

        public User(String fullName, String email, String phone, String password) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.password = password;
        }
    }

    private UserManager(Context context) {
        this.gson = new Gson();
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserManager must be initialized with context first");
        }
        return instance;
    }

    public void registerUser(String fullName, String email, String phone, String password) {
        User user = new User(fullName, email, phone, password);
        String json = gson.toJson(user);
        sharedPreferences.edit().putString(USER_KEY, json).apply();
    }

    public boolean loginUser(String email, String password) {
        String json = sharedPreferences.getString(USER_KEY, "");
        if (json.isEmpty()) return false;

        User user = gson.fromJson(json, User.class);
        if (user.email.equals(email) && user.password.equals(password)) {
            sharedPreferences.edit().putBoolean(IS_LOGGED_IN, true).apply();
            return true;
        }
        return false;
    }

    public void logout() {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, false).apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public User getUser() {
        String json = sharedPreferences.getString(USER_KEY, "");
        if (json.isEmpty()) return null;
        return gson.fromJson(json, User.class);
    }
}
