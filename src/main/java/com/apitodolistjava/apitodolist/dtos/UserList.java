package com.apitodolistjava.apitodolist.dtos;

import com.apitodolistjava.apitodolist.models.Task;
import com.apitodolistjava.apitodolist.models.User;

import java.util.List;
import java.util.UUID;

public record UserList(
        UUID id,
        String emailUser,
        String password,
        List<Task>tasks)
{
    public UserList(User user){
        this(
                user.getId(),
                user.getEmailUser(),
                user.getPassword(),
                user.getTasks());
    }

}
