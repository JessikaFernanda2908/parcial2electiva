package com.universidad.crud.service.impl;

import com.universidad.crud.model.Estudiante;
import com.universidad.crud.repository.EstudianteRepository;
import com.universidad.crud.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository repository;

    @Override
    public Estudiante crear(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    @Override
    public Estudiante obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Estudiante actualizar(Long id, Estudiante estudiante) {
        Estudiante existente = obtenerPorId(id);
        if (existente != null) {
            existente.setNombre(estudiante.getNombre());
            existente.setCorreo(estudiante.getCorreo());
            return repository.save(existente);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
