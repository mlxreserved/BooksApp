package com.example.booksapp.data.book.remote.di

import com.example.booksapp.data.book.remote.api.BookApi
import com.example.booksapp.data.book.remote.mappers.BooksDTOToBooksMapper
import com.example.booksapp.data.book.remote.repository.BookRemoteRepositoryImpl
import com.example.booksapp.domain.repository.BookRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideBookApi(retrofit: Retrofit): BookApi =
        retrofit.create(BookApi::class.java)

    @Singleton
    @Provides
    fun provideBookRepository(bookApi: BookApi, booksDTOToBooksMapper: BooksDTOToBooksMapper): BookRemoteRepository =
        BookRemoteRepositoryImpl(bookApi, booksDTOToBooksMapper)
}