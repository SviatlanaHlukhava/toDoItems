package org.example.todoapp.service;


import org.example.todoapp.entity.TodoItem;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<TodoItem> getAllTodos();
    Optional<TodoItem> getTodoById(Long id);
    TodoItem createTodoItem(TodoItem todoItem);
    TodoItem updateTodoItem(Long id, TodoItem todoItem);
    void deleteTodoItem(Long id);
}

