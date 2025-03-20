package com.example.booksapp.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booksapp.R
import com.example.booksapp.presentation.utils.SnackbarMessage

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            val isError = snackbarData.visuals.actionLabel == SnackbarMessage.ACTION_LABEL_ERROR
            val backgroundColor = if (isError) colorResource(R.color.red) else colorResource(R.color.light_blue)

            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                containerColor = backgroundColor,
                contentColor = Color.White,
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically, // Выравниваем содержимое по центру
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = snackbarData.visuals.message,
                        fontSize = 16.sp,

                    )

                    Spacer(Modifier.weight(1f))

                    IconButton(
                        onClick = { snackbarData.dismiss() },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.clear_search),
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    )
}