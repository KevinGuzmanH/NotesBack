package com.kevinproject.backtienda.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private NoteCategory category;

    @ManyToOne
    private Usuario usuario;

    @Column(length = 35,unique = true)
    private String title;

    @Column
    private String text;

    @Column
    @CreatedDate
    private LocalDate creation_date;

    @Column
    @LastModifiedDate
    private LocalDate edit_date;


}
