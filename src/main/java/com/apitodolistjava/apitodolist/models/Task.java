package com.apitodolistjava.apitodolist.models;


import com.apitodolistjava.apitodolist.dtos.CreateTask;
import com.apitodolistjava.apitodolist.dtos.EditTask;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="created_date")
    private LocalDate createdDate;
    @Column(name="update_date")
    private LocalDate updateDate;

    private String description;
    private  String detail;
    private boolean archived;

    @Column(name="user_id")
    private UUID userId;



    public Task(CreateTask newTask, UUID userId){
        this.createdDate = LocalDate.now();
        archived = false;
        this.description = newTask.description();
        this.detail = newTask.detail();
        this.userId = userId;
    }


}


