package com.egli.assignment5

object UserManager {
    val users = mutableListOf<User>()

    fun isUsernameTaken(username: String): Boolean {
        return users.any { it.username.equals(username, ignoreCase = true) }
    }
}