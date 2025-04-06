package com.universidad.crud.service;

import com.universidad.crud.model.Estudiante;
import com.universidad.crud.repository.EstudianteRepository;
import com.universidad.crud.service.impl.EstudianteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteServiceImpl estudianteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Ana");
        estudiante.setCorreo("ana@example.com");

        // Simula que el repositorio devuelve el estudiante guardado (con id asignado)
        Estudiante guardado = new Estudiante();
        guardado.setId(1L);
        guardado.setNombre("Ana");
        guardado.setCorreo("ana@example.com");

        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(guardado);

        Estudiante resultado = estudianteService.crear(estudiante);
        assertNotNull(resultado.getId());
        assertEquals("Ana", resultado.getNombre());
        verify(estudianteRepository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void testObtenerPorId_Existente() {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(1L);
        estudiante.setNombre("Ana");
        estudiante.setCorreo("ana@example.com");

        when(estudianteRepository.findById(1L)).thenReturn(Optional.of(estudiante));

        Estudiante resultado = estudianteService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
    }

    @Test
    void testObtenerPorId_NoExistente() {
        when(estudianteRepository.findById(99L)).thenReturn(Optional.empty());
        Estudiante resultado = estudianteService.obtenerPorId(99L);
        assertNull(resultado);
    }

    @Test
    void testObtenerTodos() {
        Estudiante estudiante1 = new Estudiante(1L, "Ana", "ana@example.com");
        Estudiante estudiante2 = new Estudiante(2L, "Luis", "luis@example.com");

        when(estudianteRepository.findAll()).thenReturn(List.of(estudiante1, estudiante2));

        List<Estudiante> lista = estudianteService.obtenerTodos();
        assertEquals(2, lista.size());
        verify(estudianteRepository, times(1)).findAll();
    }

    @Test
    void testActualizarEstudiante_Existente() {
        Estudiante existente = new Estudiante();
        existente.setId(1L);
        existente.setNombre("Ana");
        existente.setCorreo("ana@example.com");

        Estudiante actualizacion = new Estudiante();
        actualizacion.setNombre("Ana Actualizada");
        actualizacion.setCorreo("ana.actualizada@example.com");

        Estudiante actualizado = new Estudiante();
        actualizado.setId(1L);
        actualizado.setNombre("Ana Actualizada");
        actualizado.setCorreo("ana.actualizada@example.com");

        when(estudianteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(actualizado);

        Estudiante resultado = estudianteService.actualizar(1L, actualizacion);
        assertNotNull(resultado);
        assertEquals("Ana Actualizada", resultado.getNombre());
        verify(estudianteRepository, times(1)).findById(1L);
        verify(estudianteRepository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void testActualizarEstudiante_NoExistente() {
        Estudiante actualizacion = new Estudiante();
        actualizacion.setNombre("No existe");
        actualizacion.setCorreo("noexiste@example.com");

        when(estudianteRepository.findById(99L)).thenReturn(Optional.empty());
        Estudiante resultado = estudianteService.actualizar(99L, actualizacion);
        assertNull(resultado);
        verify(estudianteRepository, times(1)).findById(99L);
        verify(estudianteRepository, never()).save(any(Estudiante.class));
    }

    @Test
    void testEliminarEstudiante() {
        // No se espera retorno, solo verificar que se invoque deleteById
        doNothing().when(estudianteRepository).deleteById(1L);
        estudianteService.eliminar(1L);
        verify(estudianteRepository, times(1)).deleteById(1L);
    }
}
