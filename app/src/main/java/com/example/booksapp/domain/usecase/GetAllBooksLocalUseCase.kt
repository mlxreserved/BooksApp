package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.models.Books
import com.example.booksapp.domain.repository.BookLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBooksLocalUseCase @Inject constructor(
    private val bookLocalRepository: BookLocalRepository
) {

    operator fun invoke(): Flow<Books> =
        bookLocalRepository.getFavouriteBooks()

}