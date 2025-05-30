package com.inkubasi.hirehub.coreapp.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.inkubasi.hirehub.coreapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getUser(): Flow<User> {
        return dataStore.data.map {
            User(
                it[NAME_KEY] ?: "",
                it[TOKEN_KEY] ?: "",
                it[ID_KEY] ?: "",
                it[TYPE_KEY] ?: false,
                it[STATE_KEY] ?: false,
                it[STREAM_KEY] ?: "",
            )
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit {
            it[NAME_KEY] = user.name
            it[TOKEN_KEY] = user.token
            it[ID_KEY] = user.id
            it[TYPE_KEY] = user.isApplicant
            it[STATE_KEY] = user.isLogin
            it[STREAM_KEY] = user.tokenStream
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[NAME_KEY] = ""
            it[TOKEN_KEY] = ""
            it[ID_KEY] = ""
            it[TYPE_KEY] = false
            it[STATE_KEY] = false
            it[STREAM_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ID_KEY = stringPreferencesKey("id")
        private val TYPE_KEY = booleanPreferencesKey("type")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val STREAM_KEY = stringPreferencesKey("stream")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}