package tech.codeabsolute.encryption

interface DataEncryption {
    fun encrypt(input: String): String
    fun decrypt(input: String): String
}