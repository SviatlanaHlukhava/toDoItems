package org.example.todoapp.service;

import org.example.todoapp.entity.TodoItem;
import org.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<TodoItem> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public TodoItem createTodoItem(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    @Override
    public TodoItem updateTodoItem(Long id, TodoItem todoItem) {
        return todoRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setTitle(todoItem.getTitle());
                    existingItem.setDescription(todoItem.getDescription());
                    return todoRepository.save(existingItem);
                }).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @Override
    public void deleteTodoItem(Long id) {
        todoRepository.deleteById(id);
    }
}

