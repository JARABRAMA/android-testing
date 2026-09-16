package com.jarabrama.notes.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val saveNote: SaveNoteUseCase,
    val getNote: GetNoteUseCase,
    val syncNotes: SyncNotesUseCase
)
