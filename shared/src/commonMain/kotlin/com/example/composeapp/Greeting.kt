package com.example.composeapp

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }
}