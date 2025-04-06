package com.universidad.crud.controller;

import com.universidad.crud.model.Estudiante;
import com.universidad.crud.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstudianteController.class)
class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstudianteService estudianteService;

    @Test
    void testCrearEstudiante() throws Exception {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        estudiante.setNombre("Juan");
        estudiante.setCorreo("juan.perez@example.com");

        when(estudianteService.crear(any(Estudiante.class))).thenReturn(estudiante);

        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Juan\", \"correo\": \"juan.perez@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan.perez@example.com"));
    }

    @Test
    void testObtenerTodosEstudiantes() throws Exception {
        when(estudianteService.obtenerTodos()).thenReturn(List.of(
                new Estudiante(1L, "Juan", "juan.perez@example.com")
        ));

        mockMvc.perform(get("/api/estudiantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void testObtenerEstudiantePorId_Existente() throws Exception {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        estudiante.setNombre("Juan");
        estudiante.setCorreo("juan.perez@example.com");

        when(estudianteService.obtenerPorId(1L)).thenReturn(estudiante);

        mockMvc.perform(get("/api/estudiantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testObtenerEstudiantePorId_NoExistente() throws Exception {
        when(estudianteService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/estudiantes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarEstudiante() throws Exception {
        Estudiante estudianteActualizado = new Estudiante();
        estudianteActualizado.setId(1L);
        estudianteActualizado.setNombre("Juan Actualizado");
        estudianteActualizado.setCorreo("juan.actualizado@example.com");

        when(estudianteService.actualizar(eq(1L), any(Estudiante.class))).thenReturn(estudianteActualizado);

        mockMvc.perform(put("/api/estudiantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Juan Actualizado\", \"correo\": \"juan.actualizado@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"))
                .andExpect(jsonPath("$.correo").value("juan.actualizado@example.com"));
    }

    @Test
    void testEliminarEstudiante() throws Exception {
        // Asumimos que se ejecuta sin error, pero podrías configurar el service para lanzar una excepción si no existe.
        mockMvc.perform(delete("/api/estudiantes/1"))
                .andExpect(status().isOk());
    }
}
