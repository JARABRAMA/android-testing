package com.jarabrama.notes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(NotesUiState())
    val state: StateFlow<NotesUiState> = _state.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        getNotes()
        syncNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Search -> {
                _state.update { it.copy(searchQuery = event.query) }
                getNotes()
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                }
            }
            NotesEvent.Sync -> {
                syncNotes()
            }
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(state.value.searchQuery)
            .onStart { _state.update { it.copy(isLoading = true) } }
            .onEach { notes ->
                _state.update {
                    it.copy(
                        notes = notes,
                        isLoading = false,
                        error = null
                    )
                }
            }
            .catch { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ocurrió un error inesperado"
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun syncNotes() {
        viewModelScope.launch {
            _state.update { it.copy(isSyncing = true) }
            try {
                noteUseCases.syncNotes()
            } catch (e: Exception) {
                // We keep the error localized or show a snackbar (UI logic)
            } finally {
                _state.update { it.copy(isSyncing = false) }
            }
        }
    }
}

sealed class NotesEvent {
    data class Search(val query: String) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object Sync : NotesEvent()
}
