package com.mdavila_2001.practico_1_moviles_marcelo_davila

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdavila_2001.practico_1_moviles_marcelo_davila.components.BottomNavigationBar
import com.mdavila_2001.practico_1_moviles_marcelo_davila.components.InterestList
import com.mdavila_2001.practico_1_moviles_marcelo_davila.components.MyInterests
import com.mdavila_2001.practico_1_moviles_marcelo_davila.models.Interest
import com.mdavila_2001.practico_1_moviles_marcelo_davila.ui.theme.Practico1MovilesMarceloDavilaTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practico1MovilesMarceloDavilaTheme {
                var selectedTab by remember { mutableStateOf(0) }
                val interests = remember { mutableStateListOf(*getInterestsList().toTypedArray()) }
                var interestIndex by remember { mutableStateOf(0) }
                val onUpdate: (Interest) -> Unit = { updatedInterest ->
                    val index = interests.indexOfFirst { it.id == updatedInterest.id }
                    if (index != -1) {
                        interests[index] = updatedInterest
                    }
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = androidx.compose.ui.Alignment.Center
                                ) {
                                    Text(
                                        text = if (selectedTab == 0) "Descubrir" else "Mis Intereses",
                                        style = MaterialTheme.typography.headlineLarge,
//                                        color = Color.Black
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF16FA2C),
                                            Color(0xFF28E4FA),
//                                            Color(0xFFFA288E),
//                                            Color(0xFFF028FA)
                                        ),
                                        start = Offset(-100f, 0f),
                                        end = Offset(500f, 1000f)
                                    )
                                ),
                            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            )
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = selectedTab,
                            onTabSelected = { newTabIndex ->
                                selectedTab = newTabIndex
                            },
                            modifier = Modifier
                        )
                    }
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        selectedTab = selectedTab,
                        interests = interests,
                        onUpdate = onUpdate,
                        interestIndex = interestIndex,
                        onIndexChange = { interestIndex = it }
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
    onUpdate: (Interest) -> Unit,
    interestIndex: Int,
    onIndexChange: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        when(selectedTab){
            0 -> {
                InterestList(
                    interests = interests,
                    onUpdate = onUpdate,
                    index = interestIndex,
                    onIndexChange = onIndexChange,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            1 -> {
                val likedInterests = interests.filter { it.liked }
                MyInterests(
                    interests = likedInterests,
                    modifier = Modifier
                        .fillMaxSize()
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
            interestIndex = 0,
            onIndexChange = {},
            onUpdate = {}
        )
    }
}

fun getInterestsList(): List<Interest> {
    return listOf(
        Interest(
            1,
            "Tekken 3",
            "Videojuego de lucha lanzado en 1997 por Namco.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co8d7r.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scssle.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scsslf.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scsslg.webp"
            )
        ),
        Interest(
            2,
            "The King Of Fighters '98",
            "Videojuego de lucha lanzado en 1998 por SNK.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co1y8h.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/teahqwnj3mnqcefxnyfw.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/ubt4cl7e6w9xf6v9rp8e.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/ar3npk.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/fivuvrbaj10sihnaws7w.webp"
            )
        ),
        Interest(
            3,
            "Mortal Kombat II",
            "Videojuego de lucha lanzado en 1993 por Midway.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co6bxu.webp",
                "https://i.ytimg.com/vi/oYoU61lJ35s/hqdefault.jpg",
                "https://images.igdb.com/igdb/image/upload/t_720p/sc81jh.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/sc81jm.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/sc81jl.webp"
            )
        ),
        Interest(
            4,
            "Street Fighter Alpha 3",
            "Videojuego de lucha lanzado en 1998 por Capcom.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co6auw.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/njdbg1yjg8iyiez9dyzg.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/t1q9wl0zxdc2vgnrppqm.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/fndkowou2grmg3wefqsj.webp"
            )
        ),
        Interest(
            5,
            "Soul Edge",
            "Videojuego de lucha lanzado en 1995 por Namco.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co8c9s.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scsprg.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scsprh.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scspri.webp"
            )
        ),
        Interest(
            6,
            "Dragon Ball: Sparking! Zero",
            "Videojuego de lucha lanzado en 2024 por Bandai Namco.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co8bhn.webp",
                "https://i.ytimg.com/vi/N8Zm___c4B8/hqdefault.jpg",
                "https://images.igdb.com/igdb/image/upload/t_720p/scpuyg.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scpuyl.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scpuyk.webp"
            )
        ),
        Interest(
            7,
            "Guilty Gear Xrd -REVELATOR-",
            "Videojuego de lucha lanzado en 2015 por Arc System Works.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co2zs0.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/wm2fanncv686n0rcfpj5.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/gqlkfgogx76ngwgnxcav.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/xfibjclxhw5k6hs4qrw3.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/arfra.webp"
            )
        ),
        Interest(
            8,
            "Tekken 8",
            "Videojuego de lucha lanzado en 2024 por Bandai Namco.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co7lbb.webp",
                "https://i.ytimg.com/vi/jGQBmSsunT4/hqdefault.jpg",
                "https://images.igdb.com/igdb/image/upload/t_720p/scrysz.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scryt3.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/scryt6.webp"
            )
        ),
        Interest(
            9,
            "Street Fighter VI",
            "Videojuego de lucha lanzado en 2023 por Capcom.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co9wxo.webp",
                "https://i.ytimg.com/vi/AhYdrHBmTGw/hqdefault.jpg",
                "https://i.ytimg.com/vi/ilw0xDkfk2Q/hqdefault.jpg",
                "https://images.igdb.com/igdb/image/upload/t_720p/sch7rx.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/ar3t95.webp"
            )
        ),
        Interest(
            10,
            "Super Smash Bros. Ultimate",
            "Videojuego de lucha lanzado en 2018 por Nintendo.",
            arrayListOf(
                "https://images.igdb.com/igdb/image/upload/t_cover_big/co2255.webp",
                "https://i.ytimg.com/vi/GY2YQzqydSw/hqdefault.jpg",
                "https://images.igdb.com/igdb/image/upload/t_720p/notn8ahnbkglatmokc86.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/poy3a7v02a5g1wg4usfq.webp",
                "https://images.igdb.com/igdb/image/upload/t_720p/sc5rla.webp"
            )
        )
    )
}