package com.apitodolistjava.apitodolist.dtos;

import com.apitodolistjava.apitodolist.models.Task;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ResponseTask(
        @NotBlank
        UUID id,


        @NotBlank
        String description,
        @NotBlank
        String detail,


        boolean archived




) {
    public ResponseTask (Task t) {
        this(t.getId(), t.getDescription(), t.getDetail(), t.isArchived());
    }


}
