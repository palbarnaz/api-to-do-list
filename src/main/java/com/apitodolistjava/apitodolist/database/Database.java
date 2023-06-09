package com.apitodolistjava.apitodolist.database;

import com.apitodolistjava.apitodolist.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {
    public static List<User> users = new ArrayList<>();


    public  static List<User> getUsers(){
        return Database.users;
    }


    public  static  boolean userExistById(UUID id){
        var user = users.stream().filter(u -> u.getId().equals(id)).findAny();

        return user.isPresent();
    }

    public  static  boolean userExistByEmail(String email){
        var user = users.stream().filter(u -> u.getEmailUser().equals(email)).findAny();

        return user.isPresent();
    }

    public  static User getUserById(UUID id){
       return Database.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElseThrow();

    }

    public  static User getUserByEmail(String email){
        return Database.users.stream()
                .filter(u -> u.getEmailUser().equals(email))
                .findFirst().orElseThrow();

    }



}
