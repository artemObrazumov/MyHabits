package com.artem_obrazumov.habits.features.auth.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(
    name = AuthLocalStorageConstants.STORAGE_NAME
)

object AuthLocalStorageConstants {

    const val STORAGE_NAME = "auth"
    val LOCAL_USER_ID = longPreferencesKey("local_user_id")
}