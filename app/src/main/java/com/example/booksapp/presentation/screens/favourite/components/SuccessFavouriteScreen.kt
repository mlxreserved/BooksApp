package com.example.booksapp.presentation.screens.favourite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import com.example.booksapp.presentation.navigation.Route
import com.example.booksapp.presentation.screens.components.BookItem
import com.example.booksapp.presentation.screens.favourite.FavouriteViewModel

@Composable
fun SuccessFavouriteScreen(
    navHostController: NavHostController,
    books: List<Book>,
    favouriteViewModel: FavouriteViewModel,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(books, key = {it.id}) { book ->
            val updatedBook = books.find { it.id == book.id } ?: book

            BookItem(
                book = updatedBook,
                onBookClicked = { navHostController.navigate(
                    Route.BookDetails.getRouteWithArgs(updatedBook.id, updatedBook.bookInfo.isFavourite)
                ) {
                    launchSingleTop = true
                } },
                onFavouriteClicked = { favouriteViewModel.onFavouriteButtonClicked(updatedBook) },
                isFavourite = true,
            )
        }
    }
}