package com.topcoder.poseidon.storage

object CardStorage {

    fun saveCardNumber(cardNumber: String) {
        SecureStorageManager.save("card", cardNumber.toByteArray())
    }

    fun getLastSavedCardNumber(): String {
        SecureStorageManager.get("card")?.let {
            return String(it)
        }
        return ""
    }
}