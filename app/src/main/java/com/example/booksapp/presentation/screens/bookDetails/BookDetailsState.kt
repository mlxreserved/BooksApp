package com.example.booksapp.presentation.screens.bookDetails

import com.example.booksapp.domain.models.Book

sealed interface BookDetailsState {
    data class Success(val data: Book) : BookDetailsState
    data class Error(val message: String) : BookDetailsState
    data object Loading : BookDetailsState
}
