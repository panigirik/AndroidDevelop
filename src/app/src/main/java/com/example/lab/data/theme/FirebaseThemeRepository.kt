package com.example.lab.data.theme

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

class FirebaseThemeRepository {

    private val remoteConfig = FirebaseRemoteConfig.getInstance().apply {
        setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
        )
    }

    suspend fun fetchTheme(): CloudTheme {
        remoteConfig.fetchAndActivate().await()

        return CloudTheme(
            darkTheme = remoteConfig.getBoolean("dark_theme"),
            primaryColor = remoteConfig.getString("primary_color")
        )
    }
}

data class CloudTheme(
    val darkTheme: Boolean,
    val primaryColor: String
)