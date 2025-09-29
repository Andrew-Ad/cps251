package com.example.bookexamplesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudyTimerApp()
            }
        }
    }
}

@Composable
fun StudyTimerApp(isRunning: Boolean, timeRemaining: Int, sessionLength: Int, completedsessions: Int) {
    var isRunning by remember { mutableStateOf(true) }
    var timeRemaining by remember { mutableStateOf(0) }
    var sessionLength by remember { mutableStateOf(0) }
    var completedSessions by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerDisplay(timeRemaining, sessionLength)

        Spacer(modifier = Modifier.height(32.dp))

        TimerControls(isRunning, onToggleTimer = {true})

        Spacer(modifier = Modifier.height(32.dp))

        SessionSettings(sessionLength, onSessionLengthChange = {true})

        Spacer(modifier = Modifier.height(16.dp))

        Text("Completed Sessions: $completedSessions")
    }

    LaunchedEffect(isRunning) {
        while(timeRemaining != 0) {
            delay(1000)
            timeRemaining = timeRemaining - 1
        }
    }
}

@Composable
fun TimerDisplay(
    timeRemaining: Int,
    sessionLength: Int
) {
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val progress = 1f - (timeRemaining.toFloat() / (sessionLength * 60))

    Text("$minutes : $seconds")
}

@Composable
fun TimerControls(
    isRunning: Boolean,
    onToggleTimer: () -> Unit
) {
    Button(
        onClick = { isRunning = !isRunning }
    ) {
        Text(if (isRunning) "Stop" else "Start")
    }
}

@Composable
fun SessionSettings(
    sessionLength: Int,
    onSessionLengthChange: (Int) -> Unit
) { Button (
        onClick = {sessionLength = 5000},
        modifier = Modifier.width(70.dp)
    ) {
        Text("5m",)
    }

    Button (
        onClick = {sessionLength = 15000},
        modifier = Modifier.width(70.dp)
    ) {
        Text("15m")
    }

    Button (
        onClick = {sessionLength = 25000},
        modifier = Modifier.width(70.dp)
    ) {
        Text("25m")
    }

    Button (
        onClick = {sessionLength = 45000},
        modifier = Modifier.width(70.dp)
    ) {
        Text("45m")
    }
}

@Preview(showBackground = true)
@Composable
fun StudyTimerPreview() {
    StudyTimerApp()
}