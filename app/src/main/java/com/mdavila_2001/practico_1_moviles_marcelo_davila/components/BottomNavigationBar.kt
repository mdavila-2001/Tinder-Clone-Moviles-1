package com.mdavila_2001.practico_1_moviles_marcelo_davila.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier
) {
    // Implementation of the BottomNavigationBar
    NavigationBar(
        modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF16FA2C),
                        Color(0xFF28E4FA),
//                        Color(0xFFFA288E),
//                        Color(0xFFF028FA)
                    ),
                    start = Offset(-500f, 0f),
                    end = Offset(500f, 1000f)
                )
            ),
        containerColor = Color.Transparent,
    ){
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Descubrir"
                )
                   },
            label = { Text("Descurbir") }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Mis Intereses") },
            label = { Text("Mis Intereses") }
        )
    }
}