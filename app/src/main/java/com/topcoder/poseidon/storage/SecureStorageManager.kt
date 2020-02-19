package com.topcoder.poseidon.storage

import android.content.Context
import android.os.Build

object SecureStorageManager : SecureStorage {
    private var passwordStorage: SecureStorage
    init {
        if(Build.VERSION.SDK_INT >=23) {
            passwordStorage = SecureStorageSDK23()
        } else {
            passwordStorage = SecureStorageSDK18()
        }
    }


    override fun init(context: Context): Boolean {
        return passwordStorage.init(context)
    }

    override fun save(key: String, data: ByteArray) {
        passwordStorage.save(key, data)
    }

    override fun get(key: String): ByteArray? {
        return passwordStorage.get(key)
    }

    override fun remove(key: String) {
        passwordStorage.remove(key)
    }
}
