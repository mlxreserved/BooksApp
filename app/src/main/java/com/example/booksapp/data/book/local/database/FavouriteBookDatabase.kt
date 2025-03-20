package com.example.booksapp.data.book.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booksapp.data.book.local.dao.FavouriteBookDao
import com.example.booksapp.data.book.local.entities.FavouriteBookEntity

@Database(
    entities = [FavouriteBookEntity::class],
    version = 1
)
abstract class FavouriteBookDatabase : RoomDatabase() {
    abstract fun favouriteBookDao(): FavouriteBookDao
}