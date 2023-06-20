package com.apitodolistjava.apitodolist.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditTask(

        @NotBlank
       String description,
        @NotBlank
       String detail,


        boolean archived
        ) {
}
