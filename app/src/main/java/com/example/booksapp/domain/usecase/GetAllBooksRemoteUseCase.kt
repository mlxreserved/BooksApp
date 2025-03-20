package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.repository.BookLocalRepository
import com.example.booksapp.domain.repository.BookRemoteRepository
import javax.inject.Inject

class GetAllBooksRemoteUseCase @Inject constructor(
    private val bookRemoteRepository: BookRemoteRepository
) {

    suspend operator fun invoke(titleOfBook: String): Books =
        bookRemoteRepository.getAllBooksRemote(titleOfBook)

}