package com.example.myhealthassistant.data.service

class EncryptionService {
    fun encrypt(data: ByteArray): ByteArray {
        // Simulated encryption (in a real app, use proper encryption algorithms)
        return data.map { (it + 1).toByte() }.toByteArray()
    }

    fun decrypt(data: ByteArray): ByteArray {
        // Simulated decryption
        return data.map { (it - 1).toByte() }.toByteArray()
    }
}