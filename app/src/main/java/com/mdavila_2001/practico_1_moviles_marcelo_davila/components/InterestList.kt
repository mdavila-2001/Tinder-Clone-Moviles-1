package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest

@Composable
fun InterestList(
    interests: List<Interest>,
    onUpdate: (Interest) -> Unit,
    index: Int,
    onIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (index <= interests.lastIndex) {
        Box(
            modifier.padding(4.dp, 32.dp),
            contentAlignment = Alignment.Center
        ) {
            for (i in (index + 2) downTo index) {
                if (i <= interests.lastIndex) {
                    val zIndex = (index + 2) - i
                    val offsetY = zIndex * -85.dp
                    val offsetX = zIndex * 1.dp
                    val scale = 1f

                    if (i == index) {
                        SwipeCard(
                            interests[i],
                            onLike = {
                                if (!it.liked) {
                                    it.liked = true
                                    onUpdate(it)
                                }
                                onIndexChange(index + 1)
                            },
                            onDislike = {
                                if (!it.disliked) {
                                    it.disliked = true
                                    onUpdate(it)
                                }
                                onIndexChange(index + 1)
                            },
                            onNext = {
                                if (index < interests.lastIndex) {
                                    onIndexChange(index + 1)
                                }
                            },
                            onPrevious = {
                                if (index > 0) {
                                    onIndexChange(index - 1)
                                }
                            },
                            modifier = Modifier
                                .graphicsLayer {
                                    translationY = offsetY.value
                                    translationX = offsetX.value
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    } else {
                        InterestCard(
                            interests[i],
                            modifier = Modifier
                                .graphicsLayer {
                                    translationY = offsetY.value
                                    translationX = offsetX.value
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay más intereses",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}