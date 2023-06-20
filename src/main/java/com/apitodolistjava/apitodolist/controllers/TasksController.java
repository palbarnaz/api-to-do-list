package com.apitodolistjava.apitodolist.controllers;

import com.apitodolistjava.apitodolist.database.Database;
import com.apitodolistjava.apitodolist.dtos.*;
import com.apitodolistjava.apitodolist.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/tasks")

public class TasksController {

    @GetMapping("/{id}/filter/")
    public ResponseEntity listTasks(@RequestParam(required = false) String description, @RequestParam(required = false) Boolean archived, @PathVariable UUID id){

        var exist = Database.userExistById(id);
        if(!exist){
            ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));
        }

        var tasks = Database.getUserById(id).getTasks();

        if(tasks.size()>0){
            if (description != null) {
                tasks = tasks
                        .stream()
                        .filter(t -> t.getDescription() != null && t.getDescription().toLowerCase().contains((description)))
                        .toList();
            }

            if (archived != null) {
                tasks = tasks
                        .stream()
                        .filter(t -> archived == t.isArchived())
                        .toList();
            }

            return ResponseEntity.ok().body(tasks.stream().map(TasksList::new).toList());


        }

        return  ResponseEntity.badRequest().body(new ResponseError("Nenhum recado existente para filtragem!"));


    }

    @GetMapping("/{idUser}")
    public ResponseEntity getAllTasks(@PathVariable UUID idUser){

       var exist = Database.userExistById(idUser);
        if(!exist){
            ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));
        }

        var tasks = Database.getUserById(idUser).getTasks();

        return  ResponseEntity.ok().body(tasks.stream().map(TasksList::new).toList());

    }

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


        return ResponseEntity.ok().body(user.getTasks());

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
            t.setArchived((task.archived()));
        }
    }


    return ResponseEntity.ok().body(user.getTasks());

}


    @PutMapping("/{idUser}/statusTask/{idTask}")
    public  ResponseEntity editTaskStatus(@RequestBody  StatusTask newStatus, @PathVariable UUID idUser, @PathVariable UUID idTask) {
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

                t.setArchived((newStatus.archived()));
            }


        }

        return ResponseEntity.ok().body(user.getTasks());



    }
}