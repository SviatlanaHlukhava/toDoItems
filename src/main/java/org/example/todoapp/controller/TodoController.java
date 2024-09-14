package org.example.todoapp.controller;

import org.example.todoapp.entity.TodoItem;
import org.example.todoapp.exception.ResourceNotFoundException;
import org.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoItem> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        TodoItem todoItem = todoService.getTodoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        return ResponseEntity.ok(todoItem);
    }

    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem createdTodo = todoService.createTodoItem(todoItem);
        return ResponseEntity.status(201).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem updatedTodoItem) {
        TodoItem existingTodoItem = todoService.getTodoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));

        existingTodoItem.setTitle(updatedTodoItem.getTitle());
        existingTodoItem.setDescription(updatedTodoItem.getDescription());

        TodoItem updatedTodo = todoService.updateTodoItem(id, existingTodoItem);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        TodoItem existingTodoItem = todoService.getTodoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));

        todoService.deleteTodoItem(id);
        return ResponseEntity.noContent().build();
    }
}


