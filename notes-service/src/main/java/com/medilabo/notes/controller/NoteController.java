package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable Integer patientId) {
        return ResponseEntity.ok(noteService.findNotesByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        Note savedNote = noteService.saveNote(note);

        return ResponseEntity
                .created(URI.create("/api/notes/" + savedNote.getId()))
                .body(savedNote);
    }
}