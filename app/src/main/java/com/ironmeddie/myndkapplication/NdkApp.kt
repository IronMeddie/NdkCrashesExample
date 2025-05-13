package com.ironmeddie.myndkapplication

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

class NdkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = System.getenv("API_KEY")?.let { AppMetricaConfig.newConfigBuilder(it).build() }
        if (config != null) {
            AppMetrica.activate(this, config)
        }
    }
}