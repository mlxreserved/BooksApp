package com.example.booksapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.usecase.DeleteFavouriteBookUseCase
import com.example.booksapp.domain.usecase.GetAllBooksLocalUseCase
import com.example.booksapp.domain.usecase.GetAllBooksRemoteUseCase
import com.example.booksapp.domain.usecase.InsertFavouriteBookUseCase
import com.example.booksapp.presentation.utils.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val insertFavouriteBookUseCase: InsertFavouriteBookUseCase,
    private val deleteFavouriteBookUseCase: DeleteFavouriteBookUseCase,
    private val getAllBooksLocalUseCase: GetAllBooksLocalUseCase,
    private val getAllBooksRemoteUseCase: GetAllBooksRemoteUseCase
) : ViewModel() {

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    val favouriteState: StateFlow<Books> = getAllBooksLocalUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Books(emptyList())
        )


    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _snackbarState: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
    val snackbarState: StateFlow<SnackbarMessage?> = _snackbarState.asStateFlow()

    private var isFocused: Boolean = false
    private var searchJob: Job? = null

    fun clearSearchQuery() {
        _searchQuery.update { "" }
    }

    fun clearSnackbarMessage() {
        _snackbarState.update { null }
    }

    fun onFocusChange(isFocused: Boolean) {
        this.isFocused = isFocused
        if(!this.isFocused && _searchState.value !is SearchState.Loading) {
            searchJob?.cancel()
        }
    }

    fun onSearchQueryChange(currentQuery: String) {
        _searchQuery.update { currentQuery }
        if(isFocused) {
            startTimer(titleOfBook = currentQuery)
        }
    }

    fun onSearchSubmitted(titleOfBook: String) {
        searchJob?.cancel()
        getAllBooks(titleOfBook)
    }

    fun onRetryButtonPress(titleOfBook: String) {
        getAllBooks(titleOfBook)
    }

    private fun startTimer(titleOfBook: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            getAllBooks(titleOfBook)
        }
    }

    private fun getAllBooks(titleOfBook: String) {
        if(searchQuery.value.isEmpty()) return
        _searchState.update { SearchState.Loading }
        searchJob = viewModelScope.launch {
            try {
                loadBooks(titleOfBook)
            } catch (e: IOException) {
                _searchState.update { SearchState.Error(e.message.toString()) }
            } catch (e: CancellationException) {
                _searchState.update { SearchState.Loading }
            } catch (e: Exception) {
                _searchState.update { SearchState.Error(e.message.toString()) }
            }
        }
    }

    fun syncFavouriteBooks(booksFromApi: List<Book>, favouriteBooks: List<Book>) {
        val favouriteBookIds = favouriteBooks.map { it.id }.toSet()

        val syncedData = booksFromApi.map { book ->
            book.copy(
                bookInfo = book.bookInfo.copy(
                    isFavourite = favouriteBookIds.contains(
                        book.id
                    )
                )
            )
        }
        _searchState.update { SearchState.Success(Books(syncedData)) }
    }

    private suspend fun loadBooks(titleOfBook: String) {

        val booksFromApi = getAllBooksRemoteUseCase(titleOfBook)
        val favouriteBookIds = favouriteState.first().books.map { it.id }.toSet()

        val syncedData = booksFromApi.books.map { book ->
            book.copy(
                bookInfo = book.bookInfo.copy(
                    isFavourite = favouriteBookIds.contains(
                        book.id
                    )
                )
            )
        }

        _searchState.update { SearchState.Success(Books(syncedData)) }


    }

    fun onFavouriteButtonClicked(book: Book) {
        viewModelScope.launch {
            if(book.bookInfo.isFavourite) {
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
                    if(success) {
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
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun updateBookFavouriteStatus(bookId: String, isFavourite: Boolean) {
        _searchState.update { currentState ->
            if (currentState is SearchState.Success) {
                val updatedBooks = currentState.books.books.map { book ->
                    if (book.id == bookId) {
                        book.copy(bookInfo = book.bookInfo.copy(isFavourite = isFavourite))
                    } else book
                }
                SearchState.Success(Books(updatedBooks))
            } else currentState
        }
    }


    companion object {
        private const val SEARCH_DELAY = 2000L
    }

}