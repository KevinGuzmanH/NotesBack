package com.kevinproject.backtienda.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUsuario {

    @NotEmpty
    private String name;

    @NotEmpty
    private String last_name;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
