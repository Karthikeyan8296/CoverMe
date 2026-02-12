package com.example.coverme.data.di

import android.content.Context
import androidx.room.Room
import com.example.coverme.data.local.DAO.PhotosDAO
import com.example.coverme.data.local.database.AppDatabase
import com.example.coverme.data.remote.api.UnSplashAPI
import com.example.coverme.data.repository.ImageRepositoryImpl
import com.example.coverme.data.repository.StoreFavRepositoryImpl
import com.example.coverme.domain.repository.ImageRepository
import com.example.coverme.domain.repository.StoreFavRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.unsplash.com/"

//module is an container class, where we need to define the @Provide, @Bind dependency
//Container for dependency definitions
@Module
//it defines the scope, its like lifecycle container
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providerPhotosDAO(database: AppDatabase): PhotosDAO {
        return database.photosDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(UnsplashAuthInterceptor()).build()
    }

    @Provides
    @Singleton
    fun getInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideUnSplashAPI(retrofit: Retrofit): UnSplashAPI {
        return retrofit.create(UnSplashAPI::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    abstract fun bindFavRepository(
        storeFavRepositoryImpl: StoreFavRepositoryImpl
    ): StoreFavRepository
}