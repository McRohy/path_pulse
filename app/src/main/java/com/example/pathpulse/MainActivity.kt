package com.example.pathpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pathpulse.navigation.NavController
import com.example.pathpulse.ui.theme.PathPulseTheme

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


