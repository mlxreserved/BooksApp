package com.example.booksapp.data.book.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksapp.data.book.local.entities.FavouriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteBookDao {

    @Query("SELECT * FROM FavouriteBookEntity")
    fun getFavouriteBooks(): Flow<List<FavouriteBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteBook(favouriteBook: FavouriteBookEntity)

    @Delete
    suspend fun deleteFavouriteBook(favouriteBook: FavouriteBookEntity)
}