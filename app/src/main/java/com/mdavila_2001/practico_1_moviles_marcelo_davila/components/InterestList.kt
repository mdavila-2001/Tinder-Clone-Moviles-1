package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest

@Composable
fun InterestList(
    interests: List<Interest>,
    onUpdate: (Interest) -> Unit,
    modifier: Modifier = Modifier
) {
    var index by remember { mutableStateOf(0) }

    if (index <= interests.lastIndex) {
        val current = interests[index]
        SwipeCard(
            interest = current,
            onLike = {
                if (!it.liked) {
                    it.liked = true
                    it.disliked = false
                    onUpdate(it)
                }
                index++
            },
            onDislike = {
                if (!it.disliked) {
                    it.disliked = true
                    it.liked = false
                    onUpdate(it)
                }
                index++
            }
        )
    } else {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay más intereses", style = MaterialTheme.typography.bodyLarge)
        }
    }
}