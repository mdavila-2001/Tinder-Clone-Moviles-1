package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeFunction(
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    swipeThresholdPx: Float = 72.dp.value * LocalDensity.current.density,
    sensitivity: Float = 1f,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var offsetX by remember { mutableStateOf(0f) }

    val alpha by animateFloatAsState(
        targetValue = (1f - (kotlin.math.abs(offsetX) / swipeThresholdPx)).coerceIn(0f, 1f),
        label = "alpha"
    )

    val rotation by animateFloatAsState(
        targetValue = (offsetX / 20f),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        val x = offsetX
                        when {
                            x > swipeThresholdPx -> scope.launch {
                                offsetX = 500f
                                delay(50)
                                onSwipeRight()
                                Toast.makeText(context, "Te interesa", Toast.LENGTH_SHORT).show()
                                offsetX = 0f
                            }
                            x < -swipeThresholdPx -> scope.launch {
                                offsetX = -500f
                                delay(50)
                                onSwipeLeft()
                                Toast.makeText(context, "Lo eliminaremos", Toast.LENGTH_SHORT).show()
                                offsetX = 0f
                            }
                            else -> {
                                offsetX = 0f
                            }
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += (dragAmount * sensitivity)
                    }
                )
            }
            .graphicsLayer(alpha = alpha, rotationZ = rotation)
    ) {
        content()
    }
}