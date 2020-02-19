package com.topcoder.poseidon.storage

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyProperties
import android.security.keystore.KeyGenParameterSpec
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec


@RequiresApi(23)
class SecureStorageSDK23 : SecureStorage {
    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val AndroidKeyStore = "AndroidKeyStore"
    private val AES_MODE = "AES/GCM/NoPadding"
    private var encryption: ByteArray? = null
    private val FIXED_IV = "a fixed iv!!" //to increase security, IV should be generated randomly!
    private lateinit var preferences: SharedPreferences
    private val KEY_IV = "iv"
    private lateinit var keyStore: KeyStore
    private val KEY_ALIAS = "secure_key"


    override fun init(context: Context): Boolean {
        preferences = context.getSharedPreferences(
            "storage_api23",
            Context.MODE_PRIVATE
        )
        initKeyStore()
        return true
    }

    override fun save(key: String, data: ByteArray) {
        val encryptedData = encrypt(data) ?: ""
        val editor = preferences.edit()
        editor.putString(key, encryptedData)
        editor.apply()
    }

    override fun get(key: String): ByteArray? {
        return try {
            decrypt(preferences.getString(key,"")!!)
        }catch (e: Exception) {
            "".toByteArray()
        }
    }

    override fun remove(key: String) {

    }

    @Throws(
        KeyStoreException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class,
        IOException::class
    )
    private fun initKeyStore() {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)
        if (keyStore.containsAlias(KEY_ALIAS).not()) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchProviderException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IOException::class,
        InvalidAlgorithmParameterException::class,
        SignatureException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )

    fun encrypt(textToEncrypt: ByteArray): String? {

        val c = Cipher.getInstance(AES_MODE)
        c.init(
            Cipher.ENCRYPT_MODE,
            getSecretKey(),
            GCMParameterSpec(128, FIXED_IV.toByteArray())
        )
        val encodedBytes = c.doFinal(textToEncrypt)
        val encryptedBase64Encoded = Base64.encodeToString(encodedBytes, Base64.DEFAULT)
        return encryptedBase64Encoded
    }

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchProviderException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IOException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        InvalidAlgorithmParameterException::class
    )
    fun decrypt(encryptedData: String): ByteArray? {
        val c = Cipher.getInstance(AES_MODE)
        c.init(
            Cipher.DECRYPT_MODE,
            getSecretKey(),
            GCMParameterSpec(128, FIXED_IV.toByteArray())
        )
        return c.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun createKey(alias: String): SecretKey {

        val keyGenerator = KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )

        return keyGenerator.generateKey()
    }

    @Throws(
        NoSuchAlgorithmException::class,
        UnrecoverableEntryException::class,
        KeyStoreException::class
    )

    private fun getSecretKey(): java.security.Key {
        return keyStore.getKey(KEY_ALIAS, null)
    }
}