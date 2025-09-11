package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGestures
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
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
    swipeThresholdPx: Float = 72.dp.value * LocalDensity.current.density,
    sensitivity: Float = 1f,
    key: Any? = null,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var offsetX by remember(key) { mutableStateOf(0f) }
    var offsetY by remember(key) { mutableStateOf(0f) }
    var isDragging by remember(key) { mutableStateOf(false) }
    var isAnimating by remember(key) { mutableStateOf(false) }

    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        label = "offsetX"
    )

    val animatedOffsetY by animateFloatAsState(
        targetValue = offsetY,
        label = "offsetY"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isDragging) {
            val totalOffset = kotlin.math.sqrt(offsetX * offsetX + offsetY * offsetY)
            (1f - (totalOffset / swipeThresholdPx)).coerceIn(0f, 1f)
        } else 1f,
        label = "alpha"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isDragging) (offsetX / 20f) else 0f,
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(animatedOffsetX.roundToInt(), animatedOffsetY.roundToInt()) }
            .pointerInput(key) {
                detectDragGestures(
                    onDragStart = {
                        if (!isAnimating) {
                            isDragging = true
                        }
                    },
                    onDragEnd = {
                        if (!isAnimating) {
                            isDragging = false
                            val x = offsetX
                            val y = offsetY
                            val absX = kotlin.math.abs(x)
                            val absY = kotlin.math.abs(y)
                            
                            when {
                                absX > absY && x > swipeThresholdPx -> {
                                    isAnimating = true
                                    scope.launch {
                                        offsetX = size.width.toFloat()
                                        delay(300)
                                        onSwipeRight()
                                        Toast.makeText(context, "Te interesa", Toast.LENGTH_SHORT).show()
                                        offsetX = 0f
                                        offsetY = 0f
                                        isAnimating = false
                                    }
                                }
                                absX > absY && x < -swipeThresholdPx -> {
                                    isAnimating = true
                                    scope.launch {
                                        offsetX = -size.width.toFloat()
                                        delay(300)
                                        onSwipeLeft()
                                        Toast.makeText(context, "Lo eliminaremos", Toast.LENGTH_SHORT).show()
                                        offsetX = 0f
                                        offsetY = 0f
                                        isAnimating = false
                                    }
                                }
                                absY > absX && y < -swipeThresholdPx -> {
                                    isAnimating = true
                                    scope.launch {
                                        offsetY = -size.height.toFloat()
                                        delay(300)
                                        onSwipeUp()
                                        Toast.makeText(context, "Siguiente", Toast.LENGTH_SHORT).show()
                                        offsetX = 0f
                                        offsetY = 0f
                                        isAnimating = false
                                    }
                                }
                                absY > absX && y > swipeThresholdPx -> {
                                    isAnimating = true
                                    scope.launch {
                                        offsetY = size.height.toFloat()
                                        delay(300)
                                        onSwipeDown()
                                        Toast.makeText(context, "Anterior", Toast.LENGTH_SHORT).show()
                                        offsetX = 0f
                                        offsetY = 0f
                                        isAnimating = false
                                    }
                                }
                                else -> {
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        if (isDragging && !isAnimating) {
                            change.consume()
                            offsetX += (dragAmount.x * sensitivity)
                            offsetY += (dragAmount.y * sensitivity)
                        }
                    }
                )
            }
            .graphicsLayer(
                alpha = alpha,
                rotationZ = rotation
            )
    ) {
        content()
    }
}
