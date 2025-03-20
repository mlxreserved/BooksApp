package com.example.booksapp.domain.models


data class BookInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val publishedDate: String?,
    val imageLink: BookThumbnail?,
    var isFavourite: Boolean = false
)