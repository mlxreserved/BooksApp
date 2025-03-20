package com.example.booksapp.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.booksapp.R
import com.example.booksapp.domain.models.BookThumbnail
import com.example.booksapp.presentation.screens.search.SearchViewModel

@Composable
fun ThumbnailWithLike(
    bookThumbnail: String?,
    onFavouriteClicked: () -> Unit,
    isFavourite: Boolean
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(16.dp))
            .padding(bottom = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(bookThumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.thumbnail_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            IconButton(
                onClick = onFavouriteClicked,
                modifier = Modifier.size(64.dp)
                    .indication(interactionSource, null)
            ) {
                Image(
                    imageVector = if (isFavourite) ImageVector.vectorResource(R.drawable.favourite) else ImageVector.vectorResource(
                        R.drawable.unfavourite
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

