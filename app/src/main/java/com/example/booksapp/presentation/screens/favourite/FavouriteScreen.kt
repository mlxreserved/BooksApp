package com.example.booksapp.presentation.screens.favourite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.booksapp.presentation.screens.favourite.components.CustomTopBarFavourite
import com.example.booksapp.presentation.screens.favourite.components.EmptyFavouriteScreen
import com.example.booksapp.presentation.screens.favourite.components.SuccessFavouriteScreen

@Composable
fun FavouriteScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
) {
    val state by favouriteViewModel.favouriteState.collectAsStateWithLifecycle()
    val snackbarMessage by favouriteViewModel.snackbarState.collectAsStateWithLifecycle()

    LaunchedEffect(snackbarMessage) {

        snackbarMessage?.let { message ->

            snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                duration = SnackbarDuration.Short
            )

            favouriteViewModel.clearSnackbarMessage()
        }
    }

    Column(
        modifier = modifier
            .padding(
                horizontal = 20.dp
            )
    ) {

        CustomTopBarFavourite(
            onBackButtonClick = { navController.navigateUp() }
        )

        if(state.books.isEmpty()) {
            EmptyFavouriteScreen()
        } else {
            SuccessFavouriteScreen(
                books = state.books,
                favouriteViewModel = favouriteViewModel,
                navHostController = navController
            )
        }

    }



}