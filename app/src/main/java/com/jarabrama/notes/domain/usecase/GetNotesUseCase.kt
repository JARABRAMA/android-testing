package com.jarabrama.notes.domain.usecase

import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(query: String = ""): Flow<List<Note>> {
        return if (query.isBlank()) {
            repository.getNotes()
        } else {
            repository.searchNotes(query)
        }
    }
}
