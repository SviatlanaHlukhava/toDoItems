package org.example.todoapp.controller;

import org.example.todoapp.entity.TodoItem;
import org.example.todoapp.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTodoById_Success() throws Exception {
        TodoItem todoItem = new TodoItem(1L, "Test Title", "Test Description");
        when(todoService.getTodoById(anyLong())).thenReturn(java.util.Optional.of(todoItem));

        mockMvc.perform(get("/api/todos/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetTodoById_NotFound() throws Exception {
        when(todoService.getTodoById(anyLong())).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/todos/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Todo not found with id 1"));
    }

    @Test
    public void testCreateTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem(1L, "New Title", "New Description");
        when(todoService.createTodoItem(any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Description"));
    }

    @Test
    public void testUpdateTodoItem_Success() throws Exception {
        TodoItem existingTodoItem = new TodoItem(1L, "Old Title", "Old Description");
        TodoItem updatedTodoItem = new TodoItem(1L, "Updated Title", "Updated Description");
        when(todoService.getTodoById(anyLong())).thenReturn(java.util.Optional.of(existingTodoItem));
        when(todoService.updateTodoItem(anyLong(), any(TodoItem.class))).thenReturn(updatedTodoItem);

        mockMvc.perform(put("/api/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTodoItem)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testUpdateTodoItem_NotFound() throws Exception {
        TodoItem updatedTodoItem = new TodoItem(1L, "Updated Title", "Updated Description");
        when(todoService.getTodoById(anyLong())).thenReturn(java.util.Optional.empty());

        mockMvc.perform(put("/api/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTodoItem)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Todo not found with id 1"));
    }

    @Test
    public void testDeleteTodoItem_Success() throws Exception {
        TodoItem existingTodoItem = new TodoItem(1L, "Test Title", "Test Description");
        when(todoService.getTodoById(anyLong())).thenReturn(java.util.Optional.of(existingTodoItem));

        mockMvc.perform(delete("/api/todos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(todoService).deleteTodoItem(1L);
    }

    @Test
    public void testDeleteTodoItem_NotFound() throws Exception {
        when(todoService.getTodoById(anyLong())).thenReturn(java.util.Optional.empty());

        mockMvc.perform(delete("/api/todos/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Todo not found with id 1"));
    }
}
