package com.kevinproject.backtienda.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionIdHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String hash;

    @Column
    @Temporal(TemporalType.DATE)
    private Calendar expiration;

}
