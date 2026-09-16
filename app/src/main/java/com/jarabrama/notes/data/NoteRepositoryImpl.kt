package com.jarabrama.notes.data

import com.jarabrama.notes.data.local.NoteDao
import com.jarabrama.notes.data.local.toDomain
import com.jarabrama.notes.data.local.toEntity
import com.jarabrama.notes.domain.model.Note
import com.jarabrama.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getNoteById(id: Long): Flow<Note?> {
        return dao.getNoteById(id).map { it?.toDomain() }
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return dao.searchNotes(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchNotes() {
        // TODO: Implement network fetch
    }
}
