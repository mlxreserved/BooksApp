package com.example.booksapp.data.book.remote.api

import com.example.booksapp.data.book.remote.models.BookDTO
import com.example.booksapp.data.book.remote.models.BooksDTO
import com.example.booksapp.data.book.remote.utils.BookUrls.GET_BOOK_BY_ID
import com.example.booksapp.data.book.remote.utils.BookUrls.VOLUMES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET(VOLUMES)
    suspend fun getAllBooks(
        @Query("q") titleOfBook: String
    ): Response<BooksDTO>

    @GET(GET_BOOK_BY_ID)
    suspend fun getBookById(@Path("id") bookId: String): Response<BookDTO>

}