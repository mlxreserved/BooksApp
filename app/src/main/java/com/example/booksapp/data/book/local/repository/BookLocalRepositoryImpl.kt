package com.example.booksapp.data.book.local.repository

import androidx.compose.animation.fadeIn
import com.example.booksapp.data.book.local.dao.FavouriteBookDao
import com.example.booksapp.data.book.local.mappers.BookToFavouriteBookEntity
import com.example.booksapp.data.book.local.mappers.FavouriteBookEntityToBooks
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.repository.BookLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookLocalRepositoryImpl @Inject constructor(
    private val favouriteBookDao: FavouriteBookDao,
    private val bookToFavouriteBookEntity: BookToFavouriteBookEntity,
    private val favouriteBookEntityToBooks: FavouriteBookEntityToBooks

) : BookLocalRepository{
    override fun getFavouriteBooks(): Flow<Books> =
        favouriteBookDao.getFavouriteBooks()
            .map { favouriteBookEntityToBooks(it) }

    override suspend fun insertFavouriteBook(book: Book) {
        favouriteBookDao.insertFavouriteBook(bookToFavouriteBookEntity(book))
    }

    override suspend fun deleteFavouriteBook(book: Book) {
        favouriteBookDao.deleteFavouriteBook(bookToFavouriteBookEntity(book))
    }
}