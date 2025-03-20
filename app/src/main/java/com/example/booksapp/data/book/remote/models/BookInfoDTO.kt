package com.example.booksapp.data.book.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookInfoDTO(
    @SerialName("title")
    val title: String? = null,
    @SerialName("authors")
    val authors: List<String>? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("publishedDate")
    val publishedDate: String? = null,
    @SerialName("imageLinks")
    val imageLink: BookThumbnailDTO? = null
)