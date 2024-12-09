package com.example.onboardingapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility object for managing shared preferences in the onboarding app.
 * Provides methods to save, retrieve, and remove data from shared preferences.
 */
object SharedPrefUtils {

    private const val PREFS_NAME = "onboarding_prefs"

    private const val KEY_SIGNED_IN = "signed_in"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getPreferences(context).getBoolean(key, defaultValue)
    }

    fun saveIsSignedIn(context: Context, value: Boolean) {
        saveBoolean(context, KEY_SIGNED_IN, value)
    }

    fun getIsSignedIn(context: Context): Boolean {
        return getBoolean(context, KEY_SIGNED_IN)
    }

    fun remove(context: Context, key: String) {
        val editor = getPreferences(context).edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}