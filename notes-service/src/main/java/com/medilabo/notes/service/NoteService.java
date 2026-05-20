package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findNotesByPatientId(Integer patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }
}