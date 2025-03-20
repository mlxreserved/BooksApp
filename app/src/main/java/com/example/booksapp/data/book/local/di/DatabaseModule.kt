package com.example.booksapp.data.book.local.di

import android.content.Context
import androidx.room.Room
import com.example.booksapp.data.book.local.dao.FavouriteBookDao
import com.example.booksapp.data.book.local.database.FavouriteBookDatabase
import com.example.booksapp.data.book.local.mappers.BookToFavouriteBookEntity
import com.example.booksapp.data.book.local.mappers.FavouriteBookEntityToBooks
import com.example.booksapp.data.book.local.repository.BookLocalRepositoryImpl
import com.example.booksapp.domain.repository.BookLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavouriteBookDatabase =
        Room.databaseBuilder(
            context,
            FavouriteBookDatabase::class.java,
            "favourite_book_database"
        ).build()

    @Provides
    fun provideFavouriteBookDao(favouriteBookDatabase: FavouriteBookDatabase): FavouriteBookDao =
        favouriteBookDatabase.favouriteBookDao()

    @Provides
    fun provideBookLocalRepository(
        favouriteBookDao: FavouriteBookDao,
        bookToFavouriteBookEntity: BookToFavouriteBookEntity,
        favouriteBookEntityToBooks: FavouriteBookEntityToBooks
    ): BookLocalRepository =
        BookLocalRepositoryImpl(
            favouriteBookDao = favouriteBookDao,
            bookToFavouriteBookEntity = bookToFavouriteBookEntity,
            favouriteBookEntityToBooks = favouriteBookEntityToBooks
            )
}