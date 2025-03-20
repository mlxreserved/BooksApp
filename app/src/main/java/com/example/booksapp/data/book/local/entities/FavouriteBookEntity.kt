package com.example.booksapp.data.book.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteBookEntity(
    @PrimaryKey
    val id: String,
    val title: String?,
    val authors: String?,
    val description: String?,
    val publishedDate: String?,
    val imageLink: String?
)
