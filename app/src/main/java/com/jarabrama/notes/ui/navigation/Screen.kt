package com.jarabrama.notes.ui.navigation

sealed class Screen(val route: String) {
    object NoteList : Screen("note_list")
    object NoteEdit : Screen("note_edit?noteId={noteId}") {
        fun passNoteId(noteId: Long = -1L): String {
            return "note_edit?noteId=$noteId"
        }
    }
}
