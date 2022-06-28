package tech.codeabsolute.encryption

import org.koin.core.annotation.Single
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class DataEncryptionImpl : DataEncryption {

    private val key: SecretKey = generateKey(128)
    private val ivParameterSpec: IvParameterSpec = generateInitializationVector()
    private val algorithm = "AES/CBC/PKCS5Padding"

    fun generateKey(n: Int): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(n)
        return keyGenerator.generateKey()
    }

    fun getKeyFromPassword(password: String, salt: String): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), 65536, 256)
        return SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
    }

    fun generateInitializationVector(): IvParameterSpec {
        val initializationVector = ByteArray(16)
        SecureRandom().nextBytes(initializationVector)
        return IvParameterSpec(initializationVector)
    }

    override fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
        val cipherText = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }

    override fun decrypt(input: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(input))
        return String(plainText)
    }
}