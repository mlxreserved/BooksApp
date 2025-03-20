package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.models.Book
import com.example.booksapp.domain.repository.BookLocalRepository
import javax.inject.Inject

class DeleteFavouriteBookUseCase @Inject constructor(
   private val localBookRepository: BookLocalRepository
) {

    suspend operator fun invoke(book: Book) =
        localBookRepository.deleteFavouriteBook(book)

}