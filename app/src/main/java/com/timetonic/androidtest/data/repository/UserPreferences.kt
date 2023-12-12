package com.timetonic.androidtest.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "userPreferences")
@Singleton
class UserPreferences(private val context: Context) {
    private val dataStore: DataStore<Preferences> by lazy {
        context.dataStore
    }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun setAppKey(appKey: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("app_key")] = appKey
        }
    }

    suspend fun setOauthKey(oauthKey: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("oauth_key")] = oauthKey
        }
    }

    suspend fun setOU(ou: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("ou_user")] = ou
        }
    }

    suspend fun setSessKey(oauthKey: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("sess_key")] = oauthKey
        }
    }

    fun getAppKey() = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("app_key")]
    }

    fun getOauthKey() = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("oauth_key")]
    }

    fun getOU() = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("ou_user")]
    }

    fun getSessKey() = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("sess_key")]
    }

/*
    * GETTERS *
    fun getUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[stringPreferencesKey("user")]
            if (!jsonString.isNullOrEmpty()) {
                try {
                    Json.decodeFromString<User>(jsonString)
                } catch (e: Exception) {
                    Log.e(TAG, "Error decoding user", e)
                    null
                }
            } else {
                null
            }
        }
    }

    fun getUserToken() = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("user_token")]
    }

    fun getFavorites(): Flow<List<Deal>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[stringPreferencesKey("favorites")] ?: "[]"
            Json.decodeFromString(jsonString)
        }
    }

    fun getFcmToken() = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("fcm_token")]
    }

    fun getSendNotifications() = dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey("send_notifications")]
    }

    *//** SETTERS **//*
    suspend fun saveUser(user: User) {
        val jsonString = Json.encodeToString(User.serializer(), user)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user")] = jsonString
        }
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user_token")] = token
        }
    }

    suspend fun saveFavorites(favorites: List<Deal>) {
        val jsonString = Json.encodeToString(favorites)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("favorites")] = jsonString
        }
    }

    suspend fun saveFcmToken(token: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("fcm_token")] = token
        }
    }

    suspend fun saveSendNotifications(sendNotifications: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("send_notifications")] = sendNotifications
        }
    }*/
}