package org.example.todoapp.service;

import org.example.todoapp.entity.TodoItem;
import org.example.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private TodoItem todoItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoItem = new TodoItem();
        todoItem.setId(1L);
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("This is a test todo.");
    }

    @Test
    void testGetAllTodos() {
        when(todoRepository.findAll()).thenReturn(Arrays.asList(todoItem));
        List<TodoItem> todos = todoService.getAllTodos();
        assertNotNull(todos);
        assertEquals(1, todos.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testGetTodoById() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoItem));
        Optional<TodoItem> retrievedTodo = todoService.getTodoById(1L);
        assertTrue(retrievedTodo.isPresent());
        assertEquals("Test Todo", retrievedTodo.get().getTitle());
    }

    @Test
    void testCreateTodoItem() {
        when(todoRepository.save(todoItem)).thenReturn(todoItem);
        TodoItem createdTodo = todoService.createTodoItem(todoItem);
        assertNotNull(createdTodo);
        assertEquals("Test Todo", createdTodo.getTitle());
        verify(todoRepository, times(1)).save(todoItem);
    }

    @Test
    void testUpdateTodoItem() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoItem));
        when(todoRepository.save(todoItem)).thenReturn(todoItem);
        TodoItem updatedTodo = todoService.updateTodoItem(1L, todoItem);
        assertNotNull(updatedTodo);
        assertEquals("Test Todo", updatedTodo.getTitle());
    }

    @Test
    void testDeleteTodoItem() {
        doNothing().when(todoRepository).deleteById(1L);
        todoService.deleteTodoItem(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }
}

