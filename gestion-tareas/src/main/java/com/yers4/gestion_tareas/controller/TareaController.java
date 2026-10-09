package com.yers4.gestion_tareas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yers4.gestion_tareas.dto.TareaRequestDto;
import com.yers4.gestion_tareas.dto.TareaResponseDto;
import com.yers4.gestion_tareas.model.User;

import com.yers4.gestion_tareas.service.TareaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/tareas")
public class TareaController {

    private final TareaService tareaServise;
    

    @PostMapping ("/create")
    public ResponseEntity<TareaResponseDto> create(@Valid @RequestBody TareaRequestDto request){

        TareaResponseDto response = tareaServise.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping ("/verMisTareas")
    public ResponseEntity<List<TareaResponseDto>> seeAll(){
    User userActually = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    List<TareaResponseDto> response = tareaServise.seeall(userActually.getId());
    return ResponseEntity.status(HttpStatus.OK).body(response);
    
}
}
