package com.topcoder.poseidon.storage

import kotlinx.android.synthetic.main.fragment_home.*

object CardStorage {

    fun saveCardNumber(cardNumber: String) {
        SecureStorageManager.setData("card", cardNumber.toByteArray())
    }

    fun getLastSavedCardNumber(): String {
        SecureStorageManager.getData("card")?.let {
            return String(it)
        }
        return ""
    }
}