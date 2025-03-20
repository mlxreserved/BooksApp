package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.repository.BookLocalRepository
import javax.inject.Inject

class InsertFavouriteBookUseCase @Inject constructor(
    private val localRepository: BookLocalRepository
) {

    suspend operator fun invoke(book: Book) =
        localRepository.insertFavouriteBook(book)

}