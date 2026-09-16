package com.jarabrama.notes.ui.notes

import com.jarabrama.notes.domain.model.Note

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isSyncing: Boolean = false
)
