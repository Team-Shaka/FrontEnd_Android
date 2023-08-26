package com.dev.briefing.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.presentation.theme.MainPrimary


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun RootScreen(navController: NavHostController = rememberNavController()) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: HomeScreen.Home.route
    Scaffold(
//        scaffoldState = rememberScaffoldState(),
        bottomBar = {
            BottomNavigationBar(
                selectedItemPosition = when (currentRoute) {
                    HomeScreen.Home.route -> 0
                    ChatScreen.Chat.route -> 1
                    else -> 0
                }, { selectedItemPosition ->
                    when (selectedItemPosition) {
                        0 -> navController.navigate(HomeScreen.Home.route)
                        1 -> navController.navigate(ChatScreen.Chat.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MainPrimary),
                listOf(
                    BottomNavigationItem.Home,
                    BottomNavigationItem.Chat
                )
            )
        }
    ) {
        RootNavigationGraph(navController = navController)
    }
}

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME,
    ) {
        homeNavGraph(navController = navController)
        chatNavGraph(navController = navController)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val CHAT = "chat_graph"
}