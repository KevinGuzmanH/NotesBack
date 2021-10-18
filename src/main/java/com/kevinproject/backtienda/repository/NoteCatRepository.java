package com.kevinproject.backtienda.repository;


import com.kevinproject.backtienda.entity.NoteCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteCatRepository extends JpaRepository<NoteCategory,Integer> {

}
