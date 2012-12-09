package com.excilys.formation.parlezvous.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHelper {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FILENAME = "prefs";

	private SharedPreferences prefs;

	public PrefsHelper(Context context) {
		prefs = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
	}

	public void saveUsername(String username) {
		prefs.edit().putString(USERNAME, username).commit();
	}

	public void savePassword(String password) {
		prefs.edit().putString(PASSWORD, password).commit();
	}

	public String getUsername() {
		return prefs.getString(USERNAME, "");
	}

	public String getPassword() {
		return prefs.getString(PASSWORD, "");
	}

}
