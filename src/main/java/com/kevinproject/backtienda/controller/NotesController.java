package com.kevinproject.backtienda.controller;

import com.google.gson.Gson;
import com.kevinproject.backtienda.dto.Message;
import com.kevinproject.backtienda.dto.NewNote;
import com.kevinproject.backtienda.dto.UpdateNote;
import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.entity.NoteCategory;
import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.service.NoteCatService;
import com.kevinproject.backtienda.service.NoteService;
import com.kevinproject.backtienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


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
    public ResponseEntity<String> addNote(@RequestBody @Valid NewNote newNote, BindingResult result){
        if (result.hasErrors())
            throw new ValidationException("Check the fields and retry");
        if (noteService.findByTitle(newNote.getTitle()).isPresent())
            throw new RuntimeException("A note with this title already exists please change it");

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        NoteCategory noteCategory;

        if (!newNote.getCategory().isEmpty() && noteCatService.findCategoryByName(newNote.getCategory()).isPresent()){
            noteCategory = noteCatService.findCategoryByName(newNote.getCategory()).get();
        }else if (!noteCatService.findCategoryByName(newNote.getCategory()).isPresent()){
            noteCategory = noteCatService.saveCategory(NoteCategory.builder().name(newNote.getCategory()).build());;
        }else {
            noteCategory = noteCatService.saveCategory(NoteCategory.builder().name("").build());;
        }


        long milliseconds = newNote.getDoBefore();
        LocalDate utcDate = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.of("UTC")).toLocalDate();

        Note savedNote = noteService.saveNote(Note.builder()
                .title(newNote.getTitle())
                .text(newNote.getText())
                .usuario(usuario)
                .doBefore(utcDate)
                .category(noteCategory)
                .build());
        return ResponseEntity.created(URI.create("/Notes/V1/addNote")).body(gson.toJson(savedNote));
    }

    @DeleteMapping(path = "/deleteNote/{title}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteNote(@PathVariable("title") String noteTitle){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        noteService.deleteByTitleAndUsuario(noteTitle,usuario);

        return ResponseEntity.ok().body(gson.toJson(Message.builder().message("Note deleted")));
    }

    @PostMapping(path = "/updateNote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateNote(@RequestBody UpdateNote updateNote){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Note note = noteService.findByTitleAndUsuario(updateNote.getTitle(),usuario);
        note.setText(updateNote.getText());
        noteService.saveNote(note);
        return ResponseEntity.ok().body(gson.toJson(Message.builder().message("Note updated successfully")));
    }

    @GetMapping(path = "/getNotes")
    public ResponseEntity<String> getNotes(){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(gson.toJson(noteService.findAllByUsuario(usuario)));
    }

    @GetMapping(path = "/getNote/{title}")
    public ResponseEntity<String> getNotes(@PathVariable("title") String title){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(gson.toJson(noteService.findByTitleAndUsuario(title,usuario)));
    }

    @GetMapping(path = "/getNoteCategories")
    public ResponseEntity<String> getNoteCategories(){
        return ResponseEntity.ok().body(gson.toJson(noteCatService.findAll()));
    }
}
