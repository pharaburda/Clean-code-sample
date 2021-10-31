package com.example.cleancodesample.domain

interface UserRepository {
    fun getUserId(): String

    fun isPremiumUser(userId: String): Boolean
}