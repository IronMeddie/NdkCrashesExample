package com.ironmeddie.myndkapplication

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

class NdkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val apiKey = BuildConfig.API_KEY
        val config = AppMetricaConfig.newConfigBuilder(apiKey)
            .withLogs()
            .build()
        AppMetrica.activate(this, config)
    }
}