package com.jarabrama.notes.domain.validation

import com.jarabrama.notes.domain.model.Note

class NoteValidator {
    fun validate(note: Note): Note {
        val trimmedTitle = note.title.trim()
        val trimmedContent = note.content.trim()

        if (trimmedTitle.isEmpty() && trimmedContent.isEmpty()) {
            throw NoteValidationException("La nota no puede estar vacía")
        }

        if (trimmedTitle.isEmpty()) {
            throw NoteValidationException("El título es obligatorio")
        }

        if (trimmedTitle.length > 60) {
            throw NoteValidationException("El título no puede superar 60 caracteres")
        }

        if (trimmedContent.length > 5000) {
            throw NoteValidationException("El contenido no puede superar 5000 caracteres")
        }

        return note.copy(
            title = trimmedTitle,
            content = trimmedContent
        )
    }
}
