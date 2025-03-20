package com.example.booksapp.data.book.local.mappers

import com.example.booksapp.data.book.local.entities.FavouriteBookEntity
import com.example.booksapp.domain.models.Book
import javax.inject.Inject

class BookToFavouriteBookEntity @Inject constructor() : (Book) -> FavouriteBookEntity {

    override operator fun invoke(book: Book): FavouriteBookEntity =
        FavouriteBookEntity(
            id = book.id,
            title = book.bookInfo.title,
            authors = book.bookInfo.authors?.joinToString(", "),
            description = book.bookInfo.description,
            publishedDate = book.bookInfo.publishedDate,
            imageLink = book.bookInfo.imageLink?.thumbnail,
        )

}