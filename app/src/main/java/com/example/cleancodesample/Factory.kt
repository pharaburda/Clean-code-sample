package com.example.cleancodesample

import com.example.cleancodesample.data.MoviesRepositoryImpl
import com.example.cleancodesample.data.UserRepositoryImpl
import com.example.cleancodesample.domain.MoviesRepository
import com.example.cleancodesample.domain.UserRepository
import org.koin.dsl.module

val appModule = module {
    single<MoviesRepository> { MoviesRepositoryImpl() }
    factory<UserRepository> { UserRepositoryImpl() }
}