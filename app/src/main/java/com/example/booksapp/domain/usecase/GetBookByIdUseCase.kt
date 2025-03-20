package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.repository.BookRemoteRepository
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val bookRemoteRepository: BookRemoteRepository
) {

    suspend operator fun invoke(bookId: String): Book? =
        bookRemoteRepository.getBookByIdRemote(bookId = bookId)

}