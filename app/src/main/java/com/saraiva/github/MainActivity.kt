package com.saraiva.github

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.rememberNavController
import com.saraiva.github.core.network.NeworkMonitor
import com.saraiva.github.ui.navigation.RootNavigationGraph
import com.saraiva.github.ui.theme.GithubTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var networkMonitor: NeworkMonitor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkMonitor = NeworkMonitor(this)
        enableEdgeToEdge()
        setContent {
            GithubTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onPause() {
        super.onPause()
        networkMonitor.unregister()
    }

    fun checkConnection(): StateFlow<Boolean> {
        return networkMonitor.isConnected
    }
}