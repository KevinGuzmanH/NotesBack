package com.kevinproject.backtienda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNote {

        @NotBlank
        @NotNull
        private String title;

        @NotNull
        @NotBlank
        private String text;

}
