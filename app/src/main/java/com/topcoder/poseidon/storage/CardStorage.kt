package com.topcoder.poseidon.storage

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