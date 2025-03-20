package com.example.booksapp.presentation.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booksapp.R
import com.example.booksapp.presentation.screens.search.SearchViewModel

@Composable
fun CustomSearchBar(
    searchQuery: String,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    // Сбрасываем фокус, если клавиатура скрыта
    LaunchedEffect(key1 = keyboardVisible) {
        if (!keyboardVisible) {
            focusManager.clearFocus()
        }
    }

    TextField(
        value = searchQuery,
        onValueChange = { newText ->
            searchViewModel.onSearchQueryChange(newText)
        },
        leadingIcon = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.search_leading),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = { searchViewModel.clearSearchQuery() }
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.clear_search),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                searchViewModel.onSearchSubmitted(searchQuery)
            }
        ),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = colorResource(R.color.light_light_grey),
            unfocusedContainerColor = colorResource(R.color.light_light_grey),
            disabledContainerColor = colorResource(R.color.light_light_grey),
            focusedPlaceholderColor = colorResource(R.color.light_grey),
            unfocusedPlaceholderColor = colorResource(R.color.light_grey),
            disabledPlaceholderColor = colorResource(R.color.light_grey),
            focusedTextColor = colorResource(R.color.black),
            unfocusedTextColor = colorResource(R.color.black),
            disabledTextColor = colorResource(R.color.black),
            cursorColor = colorResource(R.color.black)
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search_placeholder),
                fontSize = 16.sp,
                color = colorResource(R.color.dark_grey)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 8.dp)
            .onFocusChanged { focusState ->
                searchViewModel.onFocusChange(focusState.isFocused)
            },
        )
}