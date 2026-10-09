package com.yers4.gestion_tareas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yers4.gestion_tareas.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User>  findByEmail(String email);
    
}
