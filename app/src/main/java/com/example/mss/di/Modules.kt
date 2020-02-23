package com.example.mss.di

import com.example.mss.BuildConfig
import com.example.mss.api.GithubApi
import com.example.mss.data.GithubRepository
import com.example.mss.data.GithubRepositoryImpl
import com.example.mss.db.UserDatabase
import com.example.mss.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    /* api */
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubApi::class.java)

    }

    // OkHttpClient
    single {
        OkHttpClient.Builder().addInterceptor(get() as HttpLoggingInterceptor).build()
    }

    // HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            } else {
                level = HttpLoggingInterceptor.Level.NONE

            }

        }
    }
}

val dbModule = module {
    /* room */
    single { UserDatabase.getInstance(androidApplication()) }
    single { get<UserDatabase>().userDao() }
}

val repositoryModule: Module = module {

    single { GithubRepositoryImpl(get()) as GithubRepository }
}

/*
val viewModelModule: Module = module {

    viewModel { UsersViewModel(get(), get()) }
}*/
