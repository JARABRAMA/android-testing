package com.jarabrama.notes.domain.usecase

import com.jarabrama.notes.domain.repository.NoteRepository
import javax.inject.Inject

class SyncNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() {
        repository.fetchNotes()
    }
}
