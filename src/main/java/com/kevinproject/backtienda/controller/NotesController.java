package com.kevinproject.backtienda.controller;

import com.google.gson.Gson;
import com.kevinproject.backtienda.dto.Message;
import com.kevinproject.backtienda.dto.NewNote;
import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.service.NoteCatService;
import com.kevinproject.backtienda.service.NoteService;
import com.kevinproject.backtienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;

@RestController
@RequestMapping("/Notes/V1")
public class NotesController {

    @Autowired
    Gson gson;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    NoteService noteService;

    @Autowired
    NoteCatService noteCatService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(path = "/addNote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNote(@Valid NewNote newNote, BindingResult result){
        if (result.hasErrors())
            throw new ValidationException("Check the fields and retry");
        if (noteService.findByTitle(newNote.getTitle()).isPresent())
            throw new RuntimeException("A note with this title already exists please change it");
        Note savedNote = noteService.saveNote(Note.builder()
                .title(newNote.getTitle())
                .text(newNote.getText())
                .category(noteCatService.findNoteByName(newNote.getCategory()).get())
                .build());
        return ResponseEntity.created(URI.create("/Notes/V1/addNote")).body(gson.toJson(savedNote));
    }

    @GetMapping(path = "/pepe")
    public ResponseEntity<String> pr(){
        return ResponseEntity.ok().body( gson.toJson("asd"));
    }

    @PostMapping(path = "/deleteNote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteNote(@RequestHeader("noteTitle") String noteTitle){

        noteService.deleteBytitle(noteTitle);

        return ResponseEntity.ok().body(gson.toJson(Message.builder().message("Note deleted")));
    }

    @PostMapping(path = "/updateNote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateNote(@Valid NewNote newNote,BindingResult result){

        if (result.hasErrors())
            throw new ValidationException("Check the fields and retry");

        Note note = noteService.findByTitle(newNote.getTitle()).get();
        note.setText(newNote.getText());

        return ResponseEntity.ok().body(gson.toJson(Message.builder().message("Note updated successfully")));
    }

}
