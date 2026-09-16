package com.jarabrama.notes.data.di

import android.app.Application
import androidx.room.Room
import com.jarabrama.notes.data.NoteRepositoryImpl
import com.jarabrama.notes.data.local.NoteDatabase
import com.jarabrama.notes.domain.repository.NoteRepository
import com.jarabrama.notes.domain.usecase.*
import com.jarabrama.notes.domain.validation.NoteValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteValidator(): NoteValidator {
        return NoteValidator()
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepository,
        validator: NoteValidator
    ): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            saveNote = SaveNoteUseCase(repository, validator),
            getNote = GetNoteUseCase(repository),
            syncNotes = SyncNotesUseCase(repository)
        )
    }
}
