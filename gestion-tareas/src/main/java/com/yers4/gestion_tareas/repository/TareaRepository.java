package com.yers4.gestion_tareas.repository;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.yers4.gestion_tareas.model.Tarea;

public interface TareaRepository extends JpaRepository<Tarea,Long> {

    List<Tarea> findByUserId(Long id);

    
    
}
