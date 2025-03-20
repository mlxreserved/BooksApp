package com.example.booksapp.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.booksapp.R
import com.example.booksapp.domain.models.Book
import com.example.booksapp.presentation.screens.search.SearchViewModel

@Composable
fun BookItem(
    book: Book,
    onBookClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
) {

    val bookThumbnail = book.bookInfo.imageLink?.thumbnail
    val authorsOfBook =
        book.bookInfo.authors?.joinToString(", ") ?: stringResource(R.string.default_authors)
    val titleOfBook = book.bookInfo.title ?: stringResource(R.string.default_title)

    Card (
        onClick = onBookClicked,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column {
            ThumbnailWithLike(
                bookThumbnail = bookThumbnail,
                isFavourite = isFavourite,
                onFavouriteClicked = onFavouriteClicked,
            )

            Text(
                text = authorsOfBook,
                color = colorResource(R.color.dark_grey),
                modifier = Modifier.padding(
                    bottom = 4.dp
                )
            )
            Text(
                text = titleOfBook
            )
        }
    }

}