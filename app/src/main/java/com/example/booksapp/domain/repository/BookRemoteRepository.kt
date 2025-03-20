package com.example.booksapp.domain.repository

import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books

interface BookRemoteRepository {

    suspend fun getAllBooksRemote(titleOfBook: String): Books

    suspend fun getBookByIdRemote(bookId: String): Book?

}