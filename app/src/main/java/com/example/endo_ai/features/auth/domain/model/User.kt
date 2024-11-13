package com.example.endo_ai.features.auth.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updateAt: Long = System.currentTimeMillis()
) {
    fun toMap(): Map<String, Any> = mapOf(
        "uid" to uid,
        "name" to name,
        "email" to email,
        "createAt" to createdAt,
        "updateAt" to updateAt
    )
}
