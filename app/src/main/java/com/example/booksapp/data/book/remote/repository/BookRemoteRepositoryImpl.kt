package com.example.booksapp.data.book.remote.repository

import com.example.booksapp.data.book.remote.api.BookApi
import com.example.booksapp.data.book.remote.mappers.BooksDTOToBooksMapper
import com.example.booksapp.data.book.remote.models.BookDTO
import com.example.booksapp.data.book.remote.models.BooksDTO
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.repository.BookRemoteRepository
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class BookRemoteRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val booksDTOToBooksMapper: BooksDTOToBooksMapper,

) : BookRemoteRepository{

    override suspend fun getAllBooksRemote(titleOfBook: String): Books =
        callApiBooks { bookApi.getAllBooks(titleOfBook) }

    override suspend fun getBookByIdRemote(bookId: String): Book? =
        callApiBook { bookApi.getBookById(bookId) }



    private suspend fun callApiBooks(
        api: suspend () -> Response<BooksDTO>
    ): Books {
        val response = api()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let { books ->
                return booksDTOToBooksMapper(books)
            } ?: return Books(emptyList())
        } else {
            throw IOException("${response.code()} ${response.message()}")
        }
    }

    private suspend fun callApiBook(
        api: suspend () -> Response<BookDTO>
    ): Book? {
        val response = api()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let { book ->
                return booksDTOToBooksMapper.bookDTOToBook(book)
            } ?: return null
        } else {
            throw IOException("${response.code()} ${response.message()}")
        }
    }


}