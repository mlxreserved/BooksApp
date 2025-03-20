package com.example.booksapp.presentation.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.usecase.DeleteFavouriteBookUseCase
import com.example.booksapp.domain.usecase.GetAllBooksLocalUseCase
import com.example.booksapp.domain.usecase.InsertFavouriteBookUseCase
import com.example.booksapp.presentation.screens.search.SearchState
import com.example.booksapp.presentation.utils.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllBooksLocalUseCase: GetAllBooksLocalUseCase,
    private val deleteFavouriteBookUseCase: DeleteFavouriteBookUseCase,
) : ViewModel() {

    val favouriteState: StateFlow<Books> = getAllBooksLocalUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Books(emptyList())
        )

    private val _snackbarState: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
    val snackbarState: StateFlow<SnackbarMessage?> = _snackbarState.asStateFlow()

    fun clearSnackbarMessage() {
        _snackbarState.update { null }
    }

    private fun deleteBookFromFavourite(book: Book): Boolean {
        try {
            viewModelScope.launch {
                deleteFavouriteBookUseCase(book)
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun onFavouriteButtonClicked(book: Book) {
        viewModelScope.launch {
            val success = deleteBookFromFavourite(book)
            _snackbarState.update {
                if (success) {
                    SnackbarMessage.SuccessDelete
                } else {
                    SnackbarMessage.ErrorDelete
                }
            }
        }
    }

}