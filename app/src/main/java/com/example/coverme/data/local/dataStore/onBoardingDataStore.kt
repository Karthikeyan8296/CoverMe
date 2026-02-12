package com.example.coverme.data.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("onboarding_prefs")

class OnBoardingDataStore(val context: Context) {

    companion object {
        private val HAS_ONBOARDED = booleanPreferencesKey("hasOnboarded")
    }

    //get
    suspend fun hasOnboarded(): Boolean {
        return context.dataStore.data.map { pref -> pref[HAS_ONBOARDED] ?: false }.first()
    }

    //set
    suspend fun setOnBoarding() {
        context.dataStore.edit { pref ->
            pref[HAS_ONBOARDED] = true
        }
    }
}