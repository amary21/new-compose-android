package com.example.newcompose.di

import androidx.room.Room
import com.example.newcompose.MainViewModel
import com.example.newcompose.data.IRepository
import com.example.newcompose.data.Repository
import com.example.newcompose.data.source.local.LocalDataSource
import com.example.newcompose.data.source.local.room.MovieDatabase
import com.example.newcompose.data.source.remote.RemoteDataSource
import com.example.newcompose.data.source.remote.network.ApiService
import com.example.newcompose.utils.Constant
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.URL_HEAD)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java,
            "Movie.db",
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    factory<IRepository> { Repository(get(), get(), androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel() }
}