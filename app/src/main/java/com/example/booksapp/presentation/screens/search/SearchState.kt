package com.example.booksapp.presentation.screens.search

import com.example.booksapp.domain.models.Books

sealed interface SearchState {
    data class Success (val books: Books) : SearchState
    data class Error(val message: String) : SearchState
    data object Loading : SearchState
    data object Idle: SearchState
    data object Empty: SearchState
}