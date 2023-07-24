package com.apitodolistjava.apitodolist.models;

import com.apitodolistjava.apitodolist.dtos.CreateTask;
import com.apitodolistjava.apitodolist.dtos.CreateUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="created_date")
    private LocalDate createdDate;
    @Column(name="update_date")
    private LocalDate updateDate;

    @Column(name="email")
    private String emailUser;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Task> tasks;

    public User(CreateUser newUser) {
        this.createdDate = LocalDate.now();
        this.emailUser = newUser.emailUser();
        this.password = newUser.password();
         tasks = new ArrayList<>();
    }



//    public void setNewTask(CreateTask task){
//        tasks.add(new Task(task));
//    };






}
