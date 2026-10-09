package com.yers4.gestion_tareas.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.yers4.gestion_tareas.dto.TareaRequestDto;
import com.yers4.gestion_tareas.dto.TareaResponseDto;
import com.yers4.gestion_tareas.model.Tarea;
import com.yers4.gestion_tareas.model.User;
import com.yers4.gestion_tareas.exception.RecursoNoEncontradoException;
import com.yers4.gestion_tareas.mapper.TareaMapper;
import com.yers4.gestion_tareas.repository.TareaRepository;
import com.yers4.gestion_tareas.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 

public class TareaService {

    private final TareaRepository tareaRepository;
    private final UserRepository userRepository;
    private final TareaMapper tareaMapper;

    public TareaResponseDto create (TareaRequestDto request ){

       User user = userRepository.findById(request.getIdUsuario())
        .orElseThrow(() -> new RecursoNoEncontradoException("no existe el usuario"));

        Tarea tarea = tareaMapper.toEntity(request);

       tarea.setUser(user);
       tareaRepository.save(tarea);
       return tareaMapper.toDto(tarea);
    }

    public List<TareaResponseDto> seeall(Long userId){
       
        List<Tarea> tareas = tareaRepository.findByUserId(userId);
        return tareaMapper.toDtoList(tareas);
    }
    
}
