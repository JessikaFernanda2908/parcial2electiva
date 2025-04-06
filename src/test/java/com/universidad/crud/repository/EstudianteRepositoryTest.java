package com.universidad.crud.repository;

import com.universidad.crud.model.Estudiante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EstudianteRepositoryTest {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Test
    void testGuardarEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Pedro");
        estudiante.setCorreo("pedro@example.com");

        Estudiante guardado = estudianteRepository.save(estudiante);
        assertNotNull(guardado.getId());
    }

    @Test
    void testBuscarEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Maria");
        estudiante.setCorreo("maria@example.com");

        Estudiante guardado = estudianteRepository.save(estudiante);
        Estudiante encontrado = estudianteRepository.findById(guardado.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Maria", encontrado.getNombre());
    }

    // Agregar prueba de eliminación
    @Test
    void testEliminarEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Carlos");
        estudiante.setCorreo("carlos@example.com");

        Estudiante guardado = estudianteRepository.save(estudiante);
        Long id = guardado.getId();
        assertNotNull(id);

        estudianteRepository.deleteById(id);
        Optional<Estudiante> eliminado = estudianteRepository.findById(id);
        assertTrue(eliminado.isEmpty());
    }
}
