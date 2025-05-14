package com.ironmeddie.myndkapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ironmeddie.varioqub.ui.theme.NdkTheme
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NdkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            scope.launch {
                withContext(Dispatchers.Default) {
                    NativeHelper.crashAppAbrt()
                }
            }
        }) {
            Text("abrt crash")
        }
        Button(onClick = {
            scope.launch {
                withContext(Dispatchers.Default) {
                    NativeHelper.crashAppSegv()
                }
            }
        }) {
            Text("segv crash")
        }
        Button(onClick = {
            NativeHelper.startCrash()
        }) {
            Text("NDK crash from other thread")
        }
        Button(onClick = {
            NativeHelper.circleCrash()
        }) {
            Text("NDK crash circle")
        }
        Button(onClick = {
            NativeHelper.tgkillCrash()
        }) {
            Text("NDK crash tgkill")
        }
        Button(onClick = {
            scope.launch {
                withContext(Dispatchers.IO) {
                    NativeHelper.thread()
                }
            }
        }) {
            Text("thread()")
        }
        Button(onClick = {
            AppMetrica.sendEventsBuffer()
        }) {
            Text("AppMetrica sendEventsBuffer")
        }
    }
}
