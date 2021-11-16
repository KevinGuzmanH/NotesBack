package com.kevinproject.backtienda;

import com.kevinproject.backtienda.dto.NewUsuario;
import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.entity.NoteCategory;
import com.kevinproject.backtienda.entity.SessionIdHash;
import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.service.NoteService;
import com.kevinproject.backtienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    NoteService noteService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //User
        SessionIdHash sessionIdHash = SessionIdHash.builder()
                .hash("$2a$10$0DxvQQxK.6kZ.6x8W8XvS.a.5b5j5QP4w8lz4x4pE4nCkKj.7rXK")
                .expiration(LocalDate.now())
                .build();

        Usuario usuario1 = Usuario.builder()
                .name("Kevin")
                .last_name("Perez")
                .username("kevinYGH")
                .password(passwordEncoder.encode("123456"))
                .hash(sessionIdHash)
                .build();

        SessionIdHash sessionIdHash2 = SessionIdHash.builder()
                .hash("$2a$10$0DxvQQxK.6kZ.5b5j5QP4w8lz4x4pE4nCkKj.7rXK6x8W8XvS.a.")
                .expiration(LocalDate.now())
                .build();

        Usuario usuario2 = Usuario.builder()
                .name("Andres")
                .last_name("tomas")
                .username("andre223")
                .password(passwordEncoder.encode("654321"))
                .hash(sessionIdHash2)
                .build();

        usuarioService.saveUsuario(usuario1);
        usuarioService.saveUsuario(usuario2);

        //Note

        NoteCategory noteCategory = NoteCategory.builder()
                .name("job")
                .description("Notes about job")
                .build();

        Note note = Note.builder()
                .title("test project")
                .usuario(usuario1)
                .doBefore(LocalDate.now().plusDays(2))
                .text("Add test with mocks and send to juan")
                .category(noteCategory)
                .build();

        noteService.saveNote(note);
    }
}
