package com.ironmeddie.myndkapplication

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

class NdkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = AppMetricaConfig.newConfigBuilder(Secret.API_KEY).build()
        AppMetrica.activate(this, config)
    }
}