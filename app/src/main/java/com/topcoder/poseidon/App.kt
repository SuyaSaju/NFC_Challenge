package com.topcoder.poseidon

import android.app.Application
import android.os.storage.StorageManager
import com.topcoder.poseidon.storage.SecureStorageManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SecureStorageManager.init(this)
    }
}