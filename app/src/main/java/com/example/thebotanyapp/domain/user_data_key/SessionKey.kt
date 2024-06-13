package com.example.thebotanyapp.domain.user_data_key

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SessionKey {
    val TOKEN = booleanPreferencesKey("session_token")
}
