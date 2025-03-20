package com.example.booksapp.data.book.local.mappers

import com.example.booksapp.data.book.local.entities.FavouriteBookEntity
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.BookInfo
import com.example.booksapp.domain.models.BookThumbnail
import com.example.booksapp.domain.models.Books
import javax.inject.Inject

class FavouriteBookEntityToBooks @Inject constructor() : (List<FavouriteBookEntity>) -> Books {

    override operator fun invoke(favouriteBooksEntity: List<FavouriteBookEntity>): Books =
        Books(
            books = favouriteBooksEntity.map { favouriteBookEntity -> bookFromFavouriteBookEntity(favouriteBookEntity) }
        )

    private fun bookFromFavouriteBookEntity(favouriteBookEntity: FavouriteBookEntity): Book =
        Book(
            id = favouriteBookEntity.id,
            bookInfo = bookInfoFromFavouriteBookEntity(favouriteBookEntity)
        )

    private fun bookInfoFromFavouriteBookEntity(favouriteBookEntity: FavouriteBookEntity): BookInfo =
        BookInfo(
            title = favouriteBookEntity.title,
            authors = favouriteBookEntity.authors?.split(", "),
            description = favouriteBookEntity.description,
            publishedDate = favouriteBookEntity.publishedDate,
            imageLink = imageLinkFromFavouriteBookEntity(favouriteBookEntity),
            isFavourite = true
        )

    private fun imageLinkFromFavouriteBookEntity(favouriteBookEntity: FavouriteBookEntity): BookThumbnail? =
        if(favouriteBookEntity.imageLink != null) {
            BookThumbnail(
                thumbnail = favouriteBookEntity.imageLink
            )
        } else {
            null
        }
}