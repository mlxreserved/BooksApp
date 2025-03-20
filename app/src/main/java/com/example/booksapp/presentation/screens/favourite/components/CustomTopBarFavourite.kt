package com.example.booksapp.presentation.screens.favourite.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booksapp.R

@Composable
fun CustomTopBarFavourite(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 24.dp,
            )
    ) {
        IconButton(
            onClick = { onBackButtonClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.back_button),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.favourite_title),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}