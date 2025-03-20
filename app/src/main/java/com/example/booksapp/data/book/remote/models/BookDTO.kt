package com.example.booksapp.data.book.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDTO(
    @SerialName("id")
    val id: String,
    @SerialName("volumeInfo")
    val bookInfo: BookInfoDTO
)
