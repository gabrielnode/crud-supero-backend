package com.example.produtos.controller;

import com.example.produtos.exception.ResourceNotFoundException;
import com.example.produtos.model.Task;
import com.example.produtos.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/task")
    public List<Task> getAllNotes() {
        return taskRepository.findAll();
    }

    @PostMapping("/task")
    public Task createNote(@Valid @RequestBody Task task) {
        System.out.println(task);
        return taskRepository.save(task);
    }

    @GetMapping("/task/{id}")
    public Task getNoteById(@PathVariable(value = "id") Long noteId) {
        return taskRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @PutMapping("/task/{id}")
    public Task updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Task noteDetails) {

        Task note = taskRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setDescription(noteDetails.getDescription());

        Task updatedNote = taskRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Task note = taskRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        taskRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
