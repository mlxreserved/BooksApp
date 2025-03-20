package com.example.booksapp.presentation.screens.bookDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.booksapp.R
import com.example.booksapp.presentation.screens.bookDetails.components.BookSuccessScreen
import com.example.booksapp.presentation.screens.bookDetails.components.CustomTopBarDetails
import com.example.booksapp.presentation.screens.components.ErrorScreen
import com.example.booksapp.presentation.screens.components.LoadingScreen

@Composable
fun BookDetailsScreen(
    bookId: String,
    isFavourite: Boolean,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    bookDetailsViewModel: BookDetailsViewModel = hiltViewModel()
) {
    val bookState by bookDetailsViewModel.bookState.collectAsStateWithLifecycle()
    val snackbarMessage by bookDetailsViewModel.snackbarState.collectAsStateWithLifecycle()

    LaunchedEffect(snackbarMessage) {

        snackbarMessage?.let { message ->

            snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                duration = SnackbarDuration.Short
            )

            bookDetailsViewModel.clearSnackbarMessage()
        }
    }

    LaunchedEffect(bookId) {
        bookDetailsViewModel.loadBook(bookId)
        bookDetailsViewModel.insertIsFavourite(isFavourite)
    }

    when(val currentBookState = bookState) {
        is BookDetailsState.Error -> ErrorScreen(
            onRetryButtonClicked = {bookDetailsViewModel.onRetryButtonPress(bookId)},
            errorText = stringResource(R.string.book_details_error)
        )
        BookDetailsState.Loading -> LoadingScreen()
        is BookDetailsState.Success -> {
            val loadedBook = currentBookState.data
            BookSuccessScreen(
                book = loadedBook,
                onBackButtonClick = { navController.navigateUp() },
                onFavouriteClick = { bookDetailsViewModel.onFavouriteButtonClicked(loadedBook) },
                bookDetailsViewModel = bookDetailsViewModel
            )
        }
    }

}
