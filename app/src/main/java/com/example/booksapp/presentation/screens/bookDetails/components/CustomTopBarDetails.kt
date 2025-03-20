package com.example.booksapp.presentation.screens.bookDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booksapp.R

@Composable
fun CustomTopBarDetails(
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackButtonClick() },
            modifier = Modifier.size(64.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.back_button),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onFavouriteClick,
            modifier = Modifier
                .size(64.dp)
                .indication(interactionSource, null)
        ) {
            Image(
                imageVector = if (isFavourite) ImageVector.vectorResource(R.drawable.favourite) else ImageVector.vectorResource(
                    R.drawable.unfavourite
                ),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}