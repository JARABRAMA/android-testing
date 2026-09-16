package com.jarabrama.notes.ui.detail

data class NoteDetailUiState(
    val title: String = "",
    val content: String = "",
    val tag: String = "",
    val error: String? = null,
    val isSaved: Boolean = false,
    val isLoading: Boolean = false
)
