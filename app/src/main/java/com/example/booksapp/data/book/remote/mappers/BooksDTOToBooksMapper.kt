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

class BooksDTOToBooksMapper @Inject constructor() : (BooksDTO) -> Books {

    override operator fun invoke(booksDTO: BooksDTO): Books =
        Books(
            books = booksDTO.books.map { book ->
                bookDTOToBook(book)
            }
        )

    fun bookDTOToBook(bookDTO: BookDTO): Book =
        Book(
            id = bookDTO.id,
            bookInfo = bookInfoDTOToBookInfo(bookDTO.bookInfo)
        )

    private fun bookInfoDTOToBookInfo(bookInfoDTO: BookInfoDTO): BookInfo =
        BookInfo(
            title = bookInfoDTO.title,
            publishedDate = bookInfoDTO.publishedDate,
            imageLink = bookThumbnailDTOToBookThumbnail(bookInfoDTO.imageLink),
            authors = bookInfoDTO.authors,
            description = bookInfoDTO.description
        )

    private fun bookThumbnailDTOToBookThumbnail(bookThumbnailDTO: BookThumbnailDTO?): BookThumbnail? {
        if(bookThumbnailDTO == null) return null

        return BookThumbnail(
            thumbnail = bookThumbnailDTO.thumbnail
        )
    }

}