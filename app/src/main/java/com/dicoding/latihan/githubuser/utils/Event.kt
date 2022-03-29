package com.dicoding.latihan.githubuser.utils

/**
 * Code is taken from:
 * https://gist.github.com/JoseAlcerreca
 * /5b661f1800e1e654f07cc54fe87441af
 */
open class Event<out T>(private val content: T) {
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}
