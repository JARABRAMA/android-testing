package com.jarabrama.notes.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.ui.components.NoteCard

@Composable
fun NoteListScreen(
    viewModel: NotesViewModel,
    onAddNoteClick: () -> Unit,
    onNoteClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()

    NoteListContent(
        state = state,
        onEvent = viewModel::onEvent,
        onAddNoteClick = onAddNoteClick,
        onNoteClick = onNoteClick
    )
}

@Composable
fun NoteListContent(
    state: NotesUiState,
    onEvent: (NotesEvent) -> Unit,
    onAddNoteClick: () -> Unit,
    onNoteClick: (Long) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onEvent(NotesEvent.Search(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar notas...") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (state.notes.isEmpty() && !state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No se encontraron notas")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.notes) { note ->
                    NoteCard(
                        note = note,
                        onNoteClick = { onNoteClick(note.id) }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onAddNoteClick,
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add note")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListScreenPreview() {
    val notes = listOf(
        Note(id = 1, title = "First Note", content = "Content 1", tag = "Work"),
        Note(id = 2, title = "Second Note", content = "Content 2", tag = "Personal")
    )
    NoteListContent(
        state = NotesUiState(notes = notes),
        onEvent = {},
        onAddNoteClick = {},
        onNoteClick = {}
    )
}
