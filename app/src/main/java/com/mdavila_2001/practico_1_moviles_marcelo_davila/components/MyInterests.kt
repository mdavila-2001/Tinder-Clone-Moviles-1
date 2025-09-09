package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mdavila_2001.practico_1_moviles_marcelo_davila.InterestCard
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest

@Composable
fun MyInterests(
    interests: List<Interest>,
    modifier: Modifier
) {
    // Implementation of the MyInterests screen
    val likedInterests = interests.filter { it.liked }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(likedInterests){
            InterestCard(
                interest = it,
                onLike = {},
                onDislike = {}
            )
        }
    }
}