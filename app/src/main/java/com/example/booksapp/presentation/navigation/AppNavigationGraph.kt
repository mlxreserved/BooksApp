package com.example.booksapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.booksapp.presentation.screens.bookDetails.BookDetailsScreen
import com.example.booksapp.presentation.screens.favourite.FavouriteScreen
import com.example.booksapp.presentation.screens.search.SearchScreen

@Composable
fun AppNavigationGraph(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.SearchScreen.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = Route.SearchScreen.route) {
            SearchScreen(
                navController = navController,
                snackbarHostState = snackbarHostState
            )
        }
        composable(route = Route.FavouriteScreen.route) {
            FavouriteScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
            )
        }
        composable(
            route = Route.BookDetails.route,
            arguments = listOf(
                navArgument("bookId") { type = NavType.StringType },
                navArgument("isFavourite") { type = NavType.StringType}
                )

        ) { navBackStackEntry ->
            val bookId = navBackStackEntry.arguments?.getString("bookId") ?: ""
            val isFavourite = navBackStackEntry.arguments?.getString("isFavourite").toBoolean()
            BookDetailsScreen(
                bookId = bookId,
                isFavourite = isFavourite,
                navController = navController,
                snackbarHostState = snackbarHostState
            )
        }

    }

}