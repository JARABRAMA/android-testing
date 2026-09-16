package com.jarabrama.notes.domain.usecase

import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(id: Long): Flow<Note?> {
        return repository.getNoteById(id)
    }
}
