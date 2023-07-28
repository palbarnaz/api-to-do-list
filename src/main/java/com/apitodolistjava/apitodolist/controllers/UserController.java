package com.apitodolistjava.apitodolist.controllers;
import com.apitodolistjava.apitodolist.dtos.CreateUser;
import com.apitodolistjava.apitodolist.dtos.ResponseError;
import com.apitodolistjava.apitodolist.dtos.UserList;
import com.apitodolistjava.apitodolist.models.User;
import com.apitodolistjava.apitodolist.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/users")


public class UserController {
    @Autowired
    private UserRepository repository;
    @GetMapping
    public ResponseEntity getAll(){

        return  ResponseEntity.ok().body(repository.findAll().stream().map(UserList::new).toList());

    }

    @GetMapping("/{id}")
    public  ResponseEntity getUserId(@PathVariable UUID id){

        var user = repository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

        }


        return ResponseEntity.ok().body(user);

    }

    @GetMapping("/{emailUser}/{password}")
    public  ResponseEntity loginUser( @PathVariable String emailUser, @PathVariable String password){
        var user = repository.findByEmailUser(emailUser);

        if(user.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

        }


        if(user.get().getEmailUser().equals(emailUser) && user.get().getPassword().equals(password)){
            return ResponseEntity.ok().body(user.get());

        }else{
            return ResponseEntity.badRequest().body(new ResponseError("E-mail ou senha inválido! Tente Novamente."));
        }
    }



    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUser userDto){
        var exist = repository.existsByEmailUser(userDto.emailUser());

        if(exist) return ResponseEntity.badRequest().body(new ResponseError("Email já cadastrado!"));


        var user = new User(userDto);
        repository.save(user);


        return ResponseEntity.ok().body("Usuário Criado!");

    }



}
