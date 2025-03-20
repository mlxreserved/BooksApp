package com.example.booksapp.presentation.screens.bookDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.booksapp.R
import com.example.booksapp.domain.models.Book
import com.example.booksapp.presentation.screens.bookDetails.BookDetailsState
import com.example.booksapp.presentation.screens.bookDetails.BookDetailsViewModel

@Composable
fun BookSuccessScreen(
    book: Book,
    bookDetailsViewModel: BookDetailsViewModel,
    onBackButtonClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isFavouriteState by bookDetailsViewModel.isFavourite.collectAsStateWithLifecycle()


    Column(
        modifier = modifier
    ) {
        CustomTopBarDetails(
            isFavourite = isFavouriteState,
            onFavouriteClick = onFavouriteClick,
            onBackButtonClick = onBackButtonClick
        )

        // Обложка книги
        Box(
            modifier = Modifier
                .height(250.dp)
                .aspectRatio(9f / 16f)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 14.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(book.bookInfo.imageLink?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = book.bookInfo.title,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }


        // Автор
        Text(
            text = book.bookInfo.authors?.joinToString(", ")
                ?: stringResource(R.string.default_authors),
            fontSize = 16.sp,
            color = colorResource(R.color.dark_grey),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Название книги
        Text(
            text = book.bookInfo.title ?: stringResource(R.string.favourite_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Год издания
        Text(
            text = if (book.bookInfo.publishedDate != null) "${book.bookInfo.publishedDate} г." else stringResource(
                R.string.default_publishedDate
            ),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.dark_grey),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        )

        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp
            ), // Скругление только сверху
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_light_grey)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.bookInfo.description ?: stringResource(R.string.default_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}