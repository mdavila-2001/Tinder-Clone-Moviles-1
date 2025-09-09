package com.mdavila_2001.practico_1_moviles_marcelo_davila

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.util.copy
import coil3.compose.AsyncImage
import com.mdavila_2001.practico_1_moviles_marcelo_davila.components.BottomNavigationBar
import com.mdavila_2001.practico_1_moviles_marcelo_davila.components.InterestList
import com.mdavila_2001.practico_1_moviles_marcelo_davila.components.MyInterests
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest
import com.mdavila_2001.practico_1_moviles_marcelo_davila.ui.theme.Practico1MovilesMarceloDavilaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practico1MovilesMarceloDavilaTheme {
                var selectedTab by remember { mutableStateOf(0) }
                val interests = remember { getInterestsList().toMutableList() }
                val onUpdate: (Interest) -> Unit = { updatedInterest ->
                    val index = interests.indexOfFirst { it.id == updatedInterest.id }
                    if (index != -1) {
                        interests[index] = updatedInterest
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = 0,
                            onTabSelected = { newTabIndex ->
                                selectedTab = newTabIndex
                            }
                        )
                    }
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        selectedTab = selectedTab,
                        interests = interests,
                        onUpdate = onUpdate
                    )
                }
            }
        }
    }
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
    Practico1MovilesMarceloDavilaTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen(
    modifier: Modifier,
    selectedTab: Int,
    interests: List<Interest>,
    onUpdate: (Interest) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        when(selectedTab){
            0 -> {
                InterestList(
                    interests = interests,
                    onUpdate = onUpdate,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
            1 -> {
                val likedInterests = interests.filter { it.liked }
                MyInterests(
                    interests = likedInterests,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
        }

    }
}

@Preview (showBackground = true)
@Composable
fun MainScreenPreview() {
    Practico1MovilesMarceloDavilaTheme {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            selectedTab = 0,
            interests = getInterestsList(),
            onUpdate = {}
        )
    }
}

fun getInterestsList(): List<Interest> {
    return listOf(
        Interest(
            1,
            "Traveling",
            "Exploring new places and cultures.",
            arrayListOf(
                "https://cdn2.thecatapi.com/images/MTYxMjc1OQ.jpg",
                "https://cdn2.thecatapi.com/images/EHG3sOpAM.jpg",
                "https://cdn2.thecatapi.com/images/EHG3sOpAM.jpg"
            )
        ),
        Interest(
            2,
            "Cooking",
            "Creating delicious meals and trying new recipes.",
            arrayListOf(
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_8519.jpg",
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_8519.jpg",
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_8519.jpg"
            )
        ),
        Interest(
            3,
            "Cleaning",
            "Having order and being neat",
            arrayListOf(
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_8519.jpg",
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_8519.jpg",
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_8519.jpg"
            )
        ),
    )
}