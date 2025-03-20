package com.example.booksapp.presentation.screens.bookDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.usecase.DeleteFavouriteBookUseCase
import com.example.booksapp.domain.usecase.GetBookByIdUseCase
import com.example.booksapp.domain.usecase.InsertFavouriteBookUseCase
import com.example.booksapp.presentation.screens.search.SearchState
import com.example.booksapp.presentation.utils.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val insertFavouriteBookUseCase: InsertFavouriteBookUseCase,
    private val deleteFavouriteBookUseCase: DeleteFavouriteBookUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase
) : ViewModel() {

    private val _bookState = MutableStateFlow<BookDetailsState>(BookDetailsState.Loading)
    val bookState = _bookState.asStateFlow()

    private val _snackbarState: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
    val snackbarState: StateFlow<SnackbarMessage?> = _snackbarState.asStateFlow()

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite = _isFavourite.asStateFlow()

    fun clearSnackbarMessage() {
        _snackbarState.update { null }
    }

    fun onRetryButtonPress(bookId: String) {
        loadBook(bookId = bookId)
    }

    fun insertIsFavourite(isFavourite: Boolean) {
        _isFavourite.update { isFavourite }
    }

    fun loadBook(bookId: String) {
        _bookState.update { BookDetailsState.Loading }
        viewModelScope.launch {
            try {
                val response = getBookByIdUseCase(bookId)
                _bookState.update { BookDetailsState.Success(response ?: throw Exception()) }
            } catch (e: Exception) {
                _bookState.update { BookDetailsState.Error("${e.message}") }
            }
        }

    }

    fun onFavouriteButtonClicked(book: Book) {
        viewModelScope.launch {
            if (_isFavourite.value) {
                val success = deleteBookFromFavourite(book)
                _snackbarState.update {
                    if (success) {
                        SnackbarMessage.SuccessDelete
                    } else {
                        SnackbarMessage.ErrorDelete
                    }
                }
            } else {
                val success = addBookToFavourite(book)
                _snackbarState.update {
                    if (success) {
                        SnackbarMessage.SuccessAdd
                    } else {
                        SnackbarMessage.ErrorAdd
                    }
                }
            }
        }
    }

    private fun addBookToFavourite(book: Book): Boolean {
        try {
            viewModelScope.launch {
                insertFavouriteBookUseCase(book)
                updateBookFavouriteStatus(book.id, isFavourite = true)
            }
            _isFavourite.update { true }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun deleteBookFromFavourite(book: Book): Boolean {
        try {
            viewModelScope.launch {
                deleteFavouriteBookUseCase(book)
                updateBookFavouriteStatus(book.id, isFavourite = false)
            }
            _isFavourite.update { false }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun updateBookFavouriteStatus(bookId: String, isFavourite: Boolean) {
        _bookState.update { currentState ->
            if (currentState is BookDetailsState.Success) {
                val updatedBook = currentState.data.copy(
                    bookInfo = currentState.data.bookInfo.copy(isFavourite = isFavourite)
                )
                BookDetailsState.Success(updatedBook)
            } else {
                currentState
            }
        }
    }

}