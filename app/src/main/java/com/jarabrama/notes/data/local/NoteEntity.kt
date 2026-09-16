package com.jarabrama.notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarabrama.notes.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val tag: String?,
    val updatedAt: Long
)

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        tag = tag,
        updatedAt = updatedAt
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        tag = tag,
        updatedAt = updatedAt
    )
}
