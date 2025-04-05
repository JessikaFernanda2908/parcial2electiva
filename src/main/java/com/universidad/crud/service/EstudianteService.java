package com.universidad.crud.service;

import com.universidad.crud.model.Estudiante;

import java.util.List;

public interface EstudianteService {
    Estudiante crear(Estudiante estudiante);
    Estudiante obtenerPorId(Long id);
    List<Estudiante> obtenerTodos();
    Estudiante actualizar(Long id, Estudiante estudiante);
    void eliminar(Long id);
}
