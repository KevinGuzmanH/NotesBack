package com.kevinproject.backtienda.repository;


import com.kevinproject.backtienda.entity.NoteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteCatRepository extends JpaRepository<NoteCategory,Integer> {

}
