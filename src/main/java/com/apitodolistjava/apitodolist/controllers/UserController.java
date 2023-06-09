package com.apitodolistjava.apitodolist.controllers;

import com.apitodolistjava.apitodolist.database.Database;
import com.apitodolistjava.apitodolist.dtos.CreateUser;
import com.apitodolistjava.apitodolist.dtos.ResponseError;
import com.apitodolistjava.apitodolist.dtos.UserList;
import com.apitodolistjava.apitodolist.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity getAll(){

        return  ResponseEntity.ok().body(Database.getUsers().stream().map((user) -> new UserList(user)).toList());

    }

    @GetMapping("/{idUser}/{emailUser}/{password}")
    public  ResponseEntity loginUser( @PathVariable String emailUser, @PathVariable String password){
        var checkUser = Database.userExistByEmail(emailUser);
        if(!checkUser){
            return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

        }

        var user = Database.getUserByEmail(emailUser);

        if(user.getEmailUser().equals(emailUser) && user.getPassword().equals(password)){
            return ResponseEntity.ok().body(user);
        }else{
            return ResponseEntity.badRequest().body(new ResponseError("E-mail ou senha inválido! Tente Novamente."));
        }
    }





    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUser userDto){

        if(Database.userExistByEmail(userDto.emailUser())) return ResponseEntity.badRequest().body(new ResponseError("Email já cadastrado!"));


        var user = new User(userDto);


        Database.users.add(user);

        return ResponseEntity.ok().body(user);

    }



}
