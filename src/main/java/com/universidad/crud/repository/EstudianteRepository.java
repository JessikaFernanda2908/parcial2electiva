package com.universidad.crud.repository;

import com.universidad.crud.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Estudiante e")
    Long findMaxId();

}
