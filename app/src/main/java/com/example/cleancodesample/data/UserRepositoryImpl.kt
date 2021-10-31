package com.example.cleancodesample.data

import com.example.cleancodesample.domain.UserRepository
import java.util.*

class UserRepositoryImpl: UserRepository {
    override fun getUserId(): String {
        return UUID.randomUUID().toString()
    }

    override fun isPremiumUser(userId: String): Boolean {
        return true
    }
}