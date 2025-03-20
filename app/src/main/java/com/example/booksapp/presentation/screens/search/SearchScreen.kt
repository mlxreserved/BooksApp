package com.example.booksapp.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.booksapp.R
import com.example.booksapp.presentation.screens.components.ErrorScreen
import com.example.booksapp.presentation.screens.components.LoadingScreen
import com.example.booksapp.presentation.screens.search.components.CustomSearchBar
import com.example.booksapp.presentation.screens.search.components.SearchEmptyScreen
import com.example.booksapp.presentation.screens.search.components.SearchIdleScreen
import com.example.booksapp.presentation.screens.search.components.SearchSuccessScreen

@Composable
fun SearchScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val state by searchViewModel.searchState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
    val snackbarMessage by searchViewModel.snackbarState.collectAsStateWithLifecycle()
    val favouriteState by searchViewModel.favouriteState.collectAsStateWithLifecycle()

    LaunchedEffect(snackbarMessage) {

        snackbarMessage?.let { message ->

            snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                duration = SnackbarDuration.Short
            )

            searchViewModel.clearSnackbarMessage()
        }
    }

    Column(
        modifier = modifier
            .padding(
                horizontal = 20.dp
            )
    ) {
        CustomSearchBar(
            searchQuery = searchQuery,
            searchViewModel = searchViewModel
        )

        when (val currentState = state) {
            is SearchState.Error -> {
                ErrorScreen(
                    onRetryButtonClicked = { searchViewModel.onRetryButtonPress(titleOfBook = searchQuery) },
                    errorText = stringResource(R.string.search_error)
                )
            }

            SearchState.Idle -> {
                SearchIdleScreen(modifier = modifier)
            }

            SearchState.Loading -> {
                LoadingScreen()
            }

            SearchState.Empty -> {
                SearchEmptyScreen()
            }

            is SearchState.Success -> {
                val books = currentState.books.books
                LaunchedEffect (favouriteState) {
                    searchViewModel.syncFavouriteBooks(
                        booksFromApi = books,
                        favouriteBooks = favouriteState.books
                    )
                }
                SearchSuccessScreen(
                    books = books,
                    modifier = Modifier.padding(top = 20.dp),
                    searchViewModel = searchViewModel,
                    navHostController = navController,
                )
            }
        }
    }

}