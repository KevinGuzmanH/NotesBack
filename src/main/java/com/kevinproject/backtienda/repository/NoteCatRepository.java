package com.kevinproject.backtienda.repository;


import com.kevinproject.backtienda.entity.NoteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface NoteCatRepository extends JpaRepository<NoteCategory,Integer> {
    Optional<NoteCategory> findByNameIgnoreCase(String name);
}
