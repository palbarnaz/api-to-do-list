package com.apitodolistjava.apitodolist.controllers;

import com.apitodolistjava.apitodolist.database.Database;
import com.apitodolistjava.apitodolist.dtos.*;
import com.apitodolistjava.apitodolist.models.Task;
import com.apitodolistjava.apitodolist.repositories.TaskRepository;
import com.apitodolistjava.apitodolist.repositories.UserRepository;
import com.apitodolistjava.apitodolist.repositories.specifications.TaskSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/tasks")

public class TasksController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskrepository;
    @GetMapping("/{id}/filter/")
    public ResponseEntity listTasks(@RequestParam(required = false) String description, @RequestParam(required = false) Boolean archived, @PathVariable UUID id){



        var checkUser = userRepository.findById(id);

        if(checkUser.isEmpty()) return  ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));


        var specification = TaskSpecification.filterByDescriptionAndStatus(description,archived, id);



        var data = taskrepository.findAll(specification).stream().map(
                TasksList::new
        ).toList();



        return ResponseEntity.ok(data);


    }

    @GetMapping("/{idUser}")
    public ResponseEntity getAllTasks(@PathVariable UUID idUser){

        var checkUser = userRepository.findById(idUser);
        if(checkUser.isEmpty()){
           return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));
        }



        var tasks = checkUser.get().getTasks();

        return  ResponseEntity.ok().body(tasks.stream().map(TasksList::new).toList());

    }

@PostMapping("/{idUser}")
    public ResponseEntity  createTask(@RequestBody @Valid CreateTask newTask, @PathVariable UUID idUser){

        var checkUser = userRepository.findById(idUser);
        if (checkUser.isEmpty() ) {

            return ResponseEntity.badRequest().body(new ResponseError("Usuário inválido!"));
        }

       var user = checkUser.get();


        var existTask = user.getTasks().stream().filter(t -> t.getDescription().equalsIgnoreCase(newTask.description())).findAny();

        if(existTask.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseError("Recado já cadastrado!"));
        }


            var task = new Task(newTask, idUser);
            taskrepository.save(task);

        return ResponseEntity.ok().body(new ResponseTask(task));

}


@DeleteMapping("/{idUser}/{idTask}")
    public  ResponseEntity deleteTask(@PathVariable UUID idUser, @PathVariable UUID idTask ){
   var checkUser = userRepository.findById(idUser);

    if(checkUser.isEmpty()){
        return ResponseEntity.badRequest().body(new ResponseError("Usuário não encontrado!"));

    }

    var task  = checkUser.get().getTasks().stream().filter(t-> t.getId().equals(idTask)).findAny();

    if(task.isEmpty()){
        return ResponseEntity.badRequest().body(new ResponseError("Recado não encontrado!"));
    }

    taskrepository.delete(task.get());
    return ResponseEntity.ok().body("Task deletada!");
}


@PutMapping("/{idTask}")
@Transactional
public  ResponseEntity editTask(@RequestBody @Valid EditTask taskDTO,  @PathVariable UUID idTask){

    var task =  taskrepository.findById(idTask);



    if(task.isEmpty()){
        return ResponseEntity.badRequest().body(new ResponseError("Recado não encontrado!"));
    }


    task.get().setDescription(taskDTO.description());
    task.get().setDetail(taskDTO.detail());
    task.get().setArchived(taskDTO.archived());



    return ResponseEntity.ok().body(new ResponseTask(task.get()));

}


    @PutMapping("/statusTask/{idTask}")
    @Transactional
    public  ResponseEntity editTaskStatus(@RequestBody  StatusTask newStatus, @PathVariable UUID idTask) {



        var task = taskrepository.findById(idTask);

        if(task.isEmpty()){
            return  ResponseEntity.badRequest().body("Recado não encontrado!");

        }

        task.get().setArchived(newStatus.archived());
        task.get().setUpdateDate(LocalDate.now());

        return ResponseEntity.ok().body(new ResponseTask(task.get())) ;



    }
}