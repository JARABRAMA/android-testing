package com.jarabrama.notes.domain.model

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val tag: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)
