package com.kevinproject.backtienda.repository;


import com.kevinproject.backtienda.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Integer> {

}
