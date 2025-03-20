package com.example.booksapp.data.book.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookThumbnailDTO(
    @SerialName("thumbnail")
    val thumbnail: String
)
