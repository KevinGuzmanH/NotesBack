package com.kevinproject.backtienda.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUsuario {

    @NotNull
    private String name;

    @NotNull
    private String last_name;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    private String password;

}
