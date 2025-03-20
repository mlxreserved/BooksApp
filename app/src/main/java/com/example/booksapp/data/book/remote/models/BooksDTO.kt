package com.example.booksapp.data.book.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksDTO(
    @SerialName("items")
    val books: List<BookDTO> = emptyList()
)
