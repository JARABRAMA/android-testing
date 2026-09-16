package com.jarabrama.notes.domain.validation

import com.jarabrama.notes.domain.model.Note
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class NoteValidatorTest {

    private lateinit var validator: NoteValidator

    @Before
    fun setUp() {
        validator = NoteValidator()
    }

    @Test
    fun `validate - title and content empty - throws NoteValidationException`() {
        val note = Note(title = "", content = "")
        val exception = assertThrows(NoteValidationException::class.java) {
            validator.validate(note)
        }
        assertEquals("La nota no puede estar vacía", exception.message)
    }

    @Test
    fun `validate - title empty - throws NoteValidationException`() {
        val note = Note(title = "", content = "Some content")
        val exception = assertThrows(NoteValidationException::class.java) {
            validator.validate(note)
        }
        assertEquals("El título es obligatorio", exception.message)
    }

    @Test
    fun `validate - title only spaces - throws NoteValidationException`() {
        val note = Note(title = "   ", content = "Some content")
        val exception = assertThrows(NoteValidationException::class.java) {
            validator.validate(note)
        }
        assertEquals("El título es obligatorio", exception.message)
    }

    @Test
    fun `validate - title too long - throws NoteValidationException`() {
        val longTitle = "a".repeat(61)
        val note = Note(title = longTitle, content = "Some content")
        val exception = assertThrows(NoteValidationException::class.java) {
            validator.validate(note)
        }
        assertEquals("El título no puede superar 60 caracteres", exception.message)
    }

    @Test
    fun `validate - content too long - throws NoteValidationException`() {
        val longContent = "a".repeat(5001)
        val note = Note(title = "Valid Title", content = longContent)
        val exception = assertThrows(NoteValidationException::class.java) {
            validator.validate(note)
        }
        assertEquals("El contenido no puede superar 5000 caracteres", exception.message)
    }

    @Test
    fun `validate - title needs trimming - returns trimmed title`() {
        val note = Note(title = "  Trim Me  ", content = "Content")
        val result = validator.validate(note)
        assertEquals("Trim Me", result.title)
    }

    @Test
    fun `validate - valid note - returns note`() {
        val note = Note(title = "Valid Title", content = "Valid Content")
        val result = validator.validate(note)
        assertEquals("Valid Title", result.title)
        assertEquals("Valid Content", result.content)
    }
}
