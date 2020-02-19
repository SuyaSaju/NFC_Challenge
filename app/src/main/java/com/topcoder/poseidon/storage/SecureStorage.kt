package com.topcoder.poseidon.storage

import android.content.Context

interface SecureStorage {
    fun init(context: Context): Boolean

    fun save(key: String, data: ByteArray)

    fun get(key: String): ByteArray?

    fun remove(key: String)
}