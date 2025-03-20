package com.example.booksapp.domain.repository

import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import kotlinx.coroutines.flow.Flow

interface BookLocalRepository {

    fun getFavouriteBooks(): Flow<Books>

    suspend fun insertFavouriteBook(book: Book)

    suspend fun deleteFavouriteBook(book: Book)

}