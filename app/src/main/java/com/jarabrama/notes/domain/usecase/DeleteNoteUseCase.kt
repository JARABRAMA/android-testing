package com.jarabrama.notes.domain.usecase

import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}
