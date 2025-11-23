package com.example.slidingnumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.slidingnumbers.ui.theme.SlidingNumbersTheme
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SlidingNumbersApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlidingNumbersApp() {
    var moves by remember { mutableStateOf(0) }
    var playing by remember { mutableStateOf(false) }
    var tiles by remember { mutableStateOf(listOf(1, 2, 3, 0)) }
    var draggedIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    val gridSize = 2

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Sliding Numbers Puzzle")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Moves: $moves")
                }

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (playing) "Playing" else "Paused")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { playing = true },
                    modifier = Modifier.fillMaxWidth(0.6f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "New Puzzle",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(gridSize) { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(gridSize) { col ->
                            val index = row * gridSize + col
                            val tileValue = tiles[index]
                            val isDragging = draggedIndex == index

                            val animatedOffset by animateOffsetAsState(
                                targetValue = if (isDragging) dragOffset else Offset.Zero,
                                animationSpec = if (isDragging) {
                                    tween(durationMillis = 0)
                                } else {
                                    spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                }, label = "tileOffset"
                            )

                            val scale by animateFloatAsState(
                                targetValue = if (isDragging) 1.1f else 1f,
                                animationSpec = tween(durationMillis = 200), label = "tileScale"
                            )

                            val alpha by animateFloatAsState(
                                targetValue = if (isDragging) 0.8f else 1f,
                                animationSpec = tween(durationMillis = 200), label = "tileAlpha"
                            )

                            if (tileValue == 0) {
                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                )
                            } else {
                                Card(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .offset {
                                            IntOffset(
                                                animatedOffset.x.roundToInt(),
                                                animatedOffset.y.roundToInt()
                                            )
                                        }
                                        .scale(scale)
                                        .alpha(alpha)
                                        .shadow(
                                            elevation = if (isDragging) 8.dp else 4.dp,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        // The pointerInput modifier remains here
                                        .pointerInput(index) {
                                            detectDragGestures(
                                                onDragStart = {
                                                    draggedIndex = index
                                                    dragOffset = Offset.Zero
                                                },
                                                onDragEnd = {
                                                    val emptyIndex = tiles.indexOf(0)

                                                    if (isAdjacent(index, emptyIndex, gridSize)) {
                                                        val newTiles = tiles.toMutableList()
                                                        newTiles[emptyIndex] = tiles[index]
                                                        newTiles[index] = 0
                                                        tiles = newTiles
                                                    }

                                                    draggedIndex = null
                                                    dragOffset = Offset.Zero
                                                },
                                                onDrag = { change, dragAmount ->
                                                    dragOffset += Offset(dragAmount.x, dragAmount.y)
                                                }
                                            )
                                        },

                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    shape = RoundedCornerShape(12.dp)

                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = tileValue.toString(),
                                            style = MaterialTheme.typography.headlineMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

private fun isAdjacent(index1: Int, index2: Int, gridSize: Int): Boolean {
    val row1 = index1 / gridSize
    val col1 = index1 % gridSize
    val row2 = index2 / gridSize
    val col2 = index2 % gridSize

    return (row1 == row2 && kotlin.math.abs(col1 - col2) == 1) ||
            (col1 == col2 && kotlin.math.abs(row1 - row2) == 1)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SlidingNumbersTheme {
        SlidingNumbersApp()
    }
}