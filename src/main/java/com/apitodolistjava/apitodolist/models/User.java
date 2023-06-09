package com.apitodolistjava.apitodolist.models;

import com.apitodolistjava.apitodolist.dtos.CreateTask;
import com.apitodolistjava.apitodolist.dtos.CreateUser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter


public class User {
    private UUID id;
    private String emailUser;
    private String password;
    private List<Task> tasks;

    public User(CreateUser newUser) {
        id = UUID.randomUUID();
        this.emailUser = newUser.emailUser();
        this.password = newUser.password();
         tasks = new ArrayList<>();
    }



    public void setNewTask(CreateTask task){
        tasks.add(new Task(task));
    };





//    public String generateToken(){
//        tokenLogin = UUID.randomUUID().toString();
//        return tokenLogin;
//    }

//    public boolean isAuthenticated(String token){
//        return tokenLogin != null && tokenLogin.equals(token);
//    }
}
