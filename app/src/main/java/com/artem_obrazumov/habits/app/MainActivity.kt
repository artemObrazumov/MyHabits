package com.artem_obrazumov.habits.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.artem_obrazumov.habits.common.ui.theme.HabitsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = LocalContentColor.current
                ) { innerPadding ->
                    Text("TESTT")
                    App(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}