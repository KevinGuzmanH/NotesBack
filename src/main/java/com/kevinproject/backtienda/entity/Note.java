package com.kevinproject.backtienda.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "note")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private NoteCategory category;

    @ManyToOne
    private Usuario usuario;

    @Column(length = 35,unique = true)
    private String title;

    @Column
    private String text;

    @Column
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Calendar creation_date;

    @Column
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Calendar edit_date;


}
