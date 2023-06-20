package com.apitodolistjava.apitodolist.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUser(
        @NotBlank
                @Email
        String emailUser,
        @NotBlank
                @Length(min = 6, max = 8 )
        String password
) {
}
