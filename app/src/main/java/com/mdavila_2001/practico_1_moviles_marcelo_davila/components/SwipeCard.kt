package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest

@Composable
fun SwipeCard(
    interest: Interest,
    onLike: (Interest) -> Unit,
    onDislike: (Interest) -> Unit,
    modifier: Modifier = Modifier
){
    SwipeFunction(
        onSwipeLeft = { onDislike(interest) },
        onSwipeRight = { onLike(interest) },
        sensitivity = 0.5f
    ) {
        InterestCard(
            interest = interest,
            modifier = modifier,
            onLike = { onLike(interest) },
            onDislike = { onDislike(interest) }
        )
    }
}