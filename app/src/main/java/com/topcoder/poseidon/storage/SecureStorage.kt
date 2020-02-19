package com.topcoder.poseidon.storage

import android.content.Context

interface SecureStorage {
    fun init(context: Context): Boolean

    fun setData(key: String, data: ByteArray)

    fun getData(key: String): ByteArray?

    fun remove(key: String)
}