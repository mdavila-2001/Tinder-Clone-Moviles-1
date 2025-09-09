package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest

@Composable
fun MyInterests(
    interests: List<Interest>,
    modifier: Modifier = Modifier
) {
    val likedInterests = interests.filter { it.liked }

    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (likedInterests.isEmpty()) {
            item {
                Text(
                    text = "No has agregado nada aún",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {

            items(likedInterests, key = { it.id }) { interest ->
                InterestCard(
                    interest = interest,
                    modifier = modifier,
                    onLike = {},
                    onDislike = {}
                )
            }
        }
    }
}
