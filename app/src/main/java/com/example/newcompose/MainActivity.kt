package com.example.newcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newcompose.data.model.BottomNavItem
import com.example.newcompose.ui.FavoriteScreen
import com.example.newcompose.ui.HomeScreen
import com.example.newcompose.ui.theme.MainTheme
import com.example.newcompose.ui.theme.Purple200
import com.example.newcompose.ui.theme.Purple700
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalFoundationApi
@DelicateCoroutinesApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitContentMain(viewModel)
        }
    }
}

@DelicateCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview()
@Composable
fun MyScreenPreview() {
    InitContentMain(viewModel = MainViewModel())
}

@DelicateCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun InitContentMain(viewModel: MainViewModel = MainViewModel()) {
    MainTheme() {
        val navController = rememberNavController()
        val countFavorite by viewModel.countFavorite.observeAsState(initial = 0)
        Scaffold(
            topBar ={
                CardView(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 8.dp
                        )
                        .fillMaxWidth(),
                ) {
                    TopAppBar(
                        title = { Text(text = "Movie App", textAlign = TextAlign.Center) },
                    )
                }
            },
            bottomBar = {
                CardView(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 8.dp
                        )
                        .fillMaxWidth()
                ) {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavItem(
                                name = "Home",
                                route = "home",
                                icon = Icons.Default.Home
                            ),
                            BottomNavItem(
                                name = "Favorite",
                                route = "favorite",
                                icon = Icons.Default.Favorite,
                                badgeCount = countFavorite
                            ),
                        ),
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                    )
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)){
                Navigation(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
){
    Card(
        modifier = modifier,
        elevation =  8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(content = content)
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Purple700,
                unselectedContentColor = Purple200,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0){
                            BadgeBox(
                                badgeContent = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }

                        if (selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}


@ExperimentalFoundationApi
@DelicateCoroutinesApi
@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel){
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){
            HomeScreen(viewModel.movies)
        }
        composable("favorite"){
            FavoriteScreen(viewModel.favorites)
        }
    }
}



