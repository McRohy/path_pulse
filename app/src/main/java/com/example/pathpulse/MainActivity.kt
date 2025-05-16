package com.example.pathpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pathpulse.navigation.NavController
import com.example.pathpulse.ui.theme.PathPulseTheme

/**
 * MainActivity je vstupným bodom Android aplikácie PathPulse.
 * Zabezpečuje inicializáciu a zobrazenie navigačného grafu cez NavController.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PathPulseTheme {
                NavController()
            }
        }
    }
}


