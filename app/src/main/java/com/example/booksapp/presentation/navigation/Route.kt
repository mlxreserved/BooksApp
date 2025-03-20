package com.example.booksapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.booksapp.R

sealed class Route (
    val route: String,
    val title: String,
    val iconId: Int
) {
    data object SearchScreen : Route(route  = "search", title = "Поиск", iconId = R.drawable.search_bottom_nav_bar)
    data object FavouriteScreen : Route(route = "favourite", title = "Избранное", iconId = R.drawable.favourite_bottom_nav_bar)
    data object BookDetails: Route(route = "book_details/{bookId}/{isFavourite}", "Детали книги", iconId = R.drawable.book_nav_graph) {
        fun getRouteWithArgs(bookId: String, isFavourite: Boolean): String = "book_details/$bookId/$isFavourite"
    }
}