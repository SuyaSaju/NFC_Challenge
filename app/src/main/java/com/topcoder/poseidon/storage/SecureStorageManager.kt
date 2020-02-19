package com.topcoder.poseidon.storage

import android.content.Context

object SecureStorageManager : SecureStorage {
    private var passwordStorage: SecureStorage = SecureStorageSDK18()

    override fun init(context: Context): Boolean {
        return passwordStorage.init(context)
    }

    override fun setData(key: String, data: ByteArray) {
        passwordStorage.setData(key, data)
    }

    override fun getData(key: String): ByteArray? {
        return passwordStorage.getData(key)
    }

    override fun remove(key: String) {
        passwordStorage.remove(key)
    }
}
