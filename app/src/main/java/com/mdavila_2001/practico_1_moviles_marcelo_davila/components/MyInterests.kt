package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mdavila_2001.practico_1_moviles_marcelo_davila.InterestCard
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest

@Composable
fun MyInterests(
    interests: List<Interest>,
    modifier: Modifier
) {
    // Implementation of the MyInterests screen
    val likedInterests = interests.filter { it.liked }

    if (likedInterests.isEmpty()) {
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                Text(text = "No has agregado nada aún")
            }
        }
    }

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