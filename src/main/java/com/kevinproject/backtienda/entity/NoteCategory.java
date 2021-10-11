package com.kevinproject.backtienda.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "note_category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 35, nullable = false)
    private String name;

    @Column(nullable = false)
    private String decription;
}
