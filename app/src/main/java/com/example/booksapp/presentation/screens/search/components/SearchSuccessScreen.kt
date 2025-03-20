package com.example.booksapp.presentation.screens.search.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.booksapp.domain.models.Book
import com.example.booksapp.presentation.navigation.Route
import com.example.booksapp.presentation.screens.components.BookItem
import com.example.booksapp.presentation.screens.search.SearchViewModel

@Composable
fun SearchSuccessScreen(
    navHostController: NavHostController,
    books: List<Book>,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(books, key = {it.id}) { book ->
            val updatedBook = books.find { it.id == book.id } ?: book
            val isFavourite = updatedBook.bookInfo.isFavourite

            BookItem(
                book = updatedBook,
                onBookClicked = { navHostController.navigate(
                    Route.BookDetails.getRouteWithArgs(updatedBook.id, updatedBook.bookInfo.isFavourite)
                ) {
                    launchSingleTop = true
                } },
                onFavouriteClicked = { searchViewModel.onFavouriteButtonClicked(updatedBook) },
                isFavourite = isFavourite
            )
        }
    }

}