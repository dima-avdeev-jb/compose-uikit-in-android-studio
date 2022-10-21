package com.example.composeapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform