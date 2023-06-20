package com.apitodolistjava.apitodolist.models;


import com.apitodolistjava.apitodolist.dtos.CreateTask;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Task {
    private UUID id;
    private String description;
    private  String detail;
    private boolean archived;


    public Task(CreateTask newTask){
        id = UUID.randomUUID();
        archived = false;
        this.description = newTask.description();
        this.detail = newTask.detail();
    }
}


