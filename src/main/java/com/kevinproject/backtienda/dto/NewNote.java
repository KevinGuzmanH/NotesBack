package com.kevinproject.backtienda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewNote {

    @NotNull
    private String category;

    @NotNull
    private String title;

    @NotNull
    private String text;

}
