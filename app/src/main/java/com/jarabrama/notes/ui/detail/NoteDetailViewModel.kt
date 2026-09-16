package com.jarabrama.notes.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.usecase.NoteUseCases
import com.jarabrama.notes.domain.validation.NoteValidationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailUiState())
    val state: StateFlow<NoteDetailUiState> = _state.asStateFlow()

    private var currentNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { id ->
            if (id != -1L) {
                loadNote(id)
            }
        }
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.EnteredTitle -> {
                _state.update { it.copy(title = event.value) }
            }
            is NoteDetailEvent.EnteredContent -> {
                _state.update { it.copy(content = event.value) }
            }
            is NoteDetailEvent.EnteredTag -> {
                _state.update { it.copy(tag = event.value) }
            }
            NoteDetailEvent.SaveNote -> {
                saveNote()
            }
        }
    }

    private fun loadNote(id: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            noteUseCases.getNote(id).collect { note ->
                note?.let {
                    currentNoteId = it.id
                    _state.update { state ->
                        state.copy(
                            title = it.title,
                            content = it.content,
                            tag = it.tag ?: "",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                val note = Note(
                    id = currentNoteId ?: 0L,
                    title = state.value.title,
                    content = state.value.content,
                    tag = state.value.tag.takeIf { it.isNotBlank() }
                )
                noteUseCases.saveNote(note)
                _state.update { it.copy(isSaved = true) }
            } catch (e: NoteValidationException) {
                _state.update { it.copy(error = e.message) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "No se pudo guardar la nota") }
            }
        }
    }
}

sealed class NoteDetailEvent {
    data class EnteredTitle(val value: String) : NoteDetailEvent()
    data class EnteredContent(val value: String) : NoteDetailEvent()
    data class EnteredTag(val value: String) : NoteDetailEvent()
    object SaveNote : NoteDetailEvent()
}
