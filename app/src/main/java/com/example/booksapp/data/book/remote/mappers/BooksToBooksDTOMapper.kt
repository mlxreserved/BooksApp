package com.example.booksapp.data.book.remote.mappers

import com.example.booksapp.data.book.remote.models.BookDTO
import com.example.booksapp.data.book.remote.models.BookInfoDTO
import com.example.booksapp.data.book.remote.models.BookThumbnailDTO
import com.example.booksapp.data.book.remote.models.BooksDTO
import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.models.BookInfo
import com.example.booksapp.domain.models.BookThumbnail
import com.example.booksapp.domain.models.Books
import javax.inject.Inject

class BooksToBooksDTOMapper @Inject constructor() : (Books) -> BooksDTO {

    override operator fun invoke(books: Books): BooksDTO =
        BooksDTO(
            books = books.books.map { book ->
                bookToBookDTO(book)
            }
        )

    private fun bookToBookDTO(book: Book): BookDTO =
        BookDTO(
            id = book.id,
            bookInfo = bookInfoToBookInfoDTO(book.bookInfo)
        )

    private fun bookInfoToBookInfoDTO(bookInfo: BookInfo): BookInfoDTO =
        BookInfoDTO(
            title = bookInfo.title,
            publishedDate = bookInfo.publishedDate,
            imageLink = bookThumbnailToBookThumbnailDTO(bookInfo.imageLink),
            authors = bookInfo.authors,
            description = bookInfo.description
        )

    private fun bookThumbnailToBookThumbnailDTO(bookThumbnail: BookThumbnail?): BookThumbnailDTO? {
        if(bookThumbnail == null) return null

        return BookThumbnailDTO(
            thumbnail = bookThumbnail.thumbnail
        )
    }

}