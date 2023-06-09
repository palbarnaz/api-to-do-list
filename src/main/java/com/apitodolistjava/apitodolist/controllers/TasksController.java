package com.apitodolistjava.apitodolist.controllers;

import com.apitodolistjava.apitodolist.database.Database;
import com.apitodolistjava.apitodolist.dtos.CreateTask;
import com.apitodolistjava.apitodolist.dtos.EditTask;
import com.apitodolistjava.apitodolist.dtos.ResponseError;
import com.apitodolistjava.apitodolist.dtos.StatusTask;
import com.apitodolistjava.apitodolist.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TasksController {

@PostMapping("/{idUser}")
    public ResponseEntity  createTask(@RequestBody @Valid CreateTask newTask, @PathVariable UUID idUser){

        var existUser = Database.userExistById(idUser);

        if (!existUser ) {

            return ResponseEntity.badRequest().body(new ResponseError("Usuário inválido!"));
        }

        var user = Database.getUserById(idUser);



        var existTask = user.getTasks().stream().filter(t -> t.getDescription().equalsIgnoreCase(newTask.description())).findAny();

        if(existTask.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseError("Recado já cadastrado!"));
        }


            user.setNewTask(newTask);


        return ResponseEntity.ok().body(user);

}


@DeleteMapping("/{idUser}/{idTask}")
    public  ResponseEntity deleteTask(@PathVariable UUID idUser, @PathVariable UUID idTask ){

    var checkUser = Database.userExistById(idUser);
    if(!checkUser){
        return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

    }


    var user = Database.getUserById(idUser);



    var task = user.getTasks().stream().filter(t-> t.getId().equals(idTask)).findAny();

    if(task.isEmpty()){
        return ResponseEntity.badRequest().body(new ResponseError("Recado não encontrado!"));
    }

    user.getTasks().remove(task.get());

    return ResponseEntity.ok().body(user.getTasks());
}


@PutMapping("/{idUser}/{idTask}")
public  ResponseEntity editTask(@RequestBody @Valid EditTask task, @PathVariable UUID idUser, @PathVariable UUID idTask){
    var checkUser = Database.userExistById(idUser);
    if(!checkUser){
        return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

    }
    var user = Database.getUserById(idUser);

    var taskEdit = user.getTasks().stream().filter(t-> t.getId().equals(idTask)).findAny();

    if(taskEdit.isEmpty()){
        return ResponseEntity.badRequest().body(new ResponseError("Recado não encontrado!"));
    }

    for(Task t : user.getTasks()){
        if(t == taskEdit.get()){
            t.setDescription(task.description());
            t.setDetail(task.detail());
            t.setArquivar((task.arquivar()));
        }
    }


    return ResponseEntity.ok().body(user.getTasks());

}


    @PutMapping("/{idUser}/statusTask/{idTask}")
    public  ResponseEntity editTaskStatus(@RequestBody @Valid StatusTask newStatus, @PathVariable UUID idUser, @PathVariable UUID idTask) {

        var checkUser = Database.userExistById(idUser);
        if(!checkUser){
            return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

        }


        var user = Database.getUserById(idUser);


        var taskEdit = user.getTasks().stream().filter(t -> t.getId().equals(idTask)).findAny();

        if (taskEdit.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseError("Recado não encontrado!"));
        }

        for (Task t : user.getTasks()) {
            if (t == taskEdit.get()) {

                t.setArquivar((newStatus.arquivar()));
            }


        }

        return ResponseEntity.ok().body(user.getTasks());


    }
}