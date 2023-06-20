package com.apitodolistjava.apitodolist.dtos;

import com.apitodolistjava.apitodolist.models.Task;

import java.util.UUID;

public record TasksList(
        UUID id,
        String description,
        String detail,
        boolean archived
) {




    public TasksList(Task t){
        this(t.getId(), t.getDescription(), t.getDetail(), t.isArchived());

    }
}
