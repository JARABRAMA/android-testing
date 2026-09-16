package com.jarabrama.notes.domain.usecase

import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.repository.NoteRepository
import com.jarabrama.notes.domain.validation.NoteValidator
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val validator: NoteValidator
) {
    suspend operator fun invoke(note: Note) {
        val validatedNote = validator.validate(note)
        val finalNote = validatedNote.copy(updatedAt = System.currentTimeMillis())
        
        if (finalNote.id == 0L) {
            repository.insertNote(finalNote)
        } else {
            repository.updateNote(finalNote)
        }
    }
}
