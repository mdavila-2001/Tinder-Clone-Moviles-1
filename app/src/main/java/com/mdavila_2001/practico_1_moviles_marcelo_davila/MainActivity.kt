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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
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
import coil3.compose.AsyncImage
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
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
fun InterestCard(
    interest: Interest,
    modifier: Modifier = Modifier,
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { interest.images.size })

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                // Carrusel de imágenes
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) { page ->
                        AsyncImage(
                            model = interest.images[page],
                            contentDescription = interest.name,
                            placeholder = painterResource(id = R.drawable.ic_launcher_background),
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(pagerState.pageCount) { i ->
                            val selected = pagerState.currentPage == i
                            Box(
                                Modifier
                                    .size(if (selected) 10.dp else 8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                    )
                            )
                        }
                    }
                }
            }

            item {
                // Información del interés
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        interest.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(
                        Modifier.height(6.dp)
                    )
                    Text(
                        interest.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
            }

            item {
                // Botones de Like / Dislike
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    FilledIconButton(
                        onClick = onDislike
                    ) {
                        Text("No me interesa")
                    }
                    FilledIconButton(
                        onClick = onLike
                    ) {
                        Text("Me interesa")
                    }
                }
            }
        }
    }
}


@Composable
fun SwipeFunction(
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    swipeThresholdPx: Float = 72.dp.value * LocalDensity.current.density,
    sensitivity: Float = 1f,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
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
                                // swipe right
                                offsetX = 500f
                                delay(300)
                                offsetX = 0f
                                onSwipeRight(); offsetX.coerceAtLeast(0f)
                            }

                            x < -swipeThresholdPx -> scope.launch {
                                // swipe left
                                offsetX = -500f
                                delay(300)
                                offsetX = 0f
                                onSwipeLeft(); offsetX.coerceAtMost(0f)
                            }

                            else -> {
                                // return to initial position
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

@Composable
fun MainScreen(modifier: Modifier) {
    val interests = getInterestsList().toMutableList()
    InterestList(
        interests = interests,
        onUpdate = { updatedInterest ->
            val index = interests.indexOfFirst { it.id == updatedInterest.id }
            if (index != -1) {
                interests[index] = updatedInterest
            }
        },
        modifier = modifier.padding(8.dp)
    )
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
    )
}