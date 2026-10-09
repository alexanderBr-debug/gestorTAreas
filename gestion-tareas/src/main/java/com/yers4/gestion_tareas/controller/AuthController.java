package com.yers4.gestion_tareas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yers4.gestion_tareas.dto.UserRequestDto;
import com.yers4.gestion_tareas.dto.UserResponseDto;
import com.yers4.gestion_tareas.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/Auth")
public class AuthController {

    private final UserService userService;

    @PostMapping ("/registre")
    public ResponseEntity<UserResponseDto> registre(@Valid @RequestBody UserRequestDto request){
        UserResponseDto response = userService.registre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  
    }

    @PostMapping ("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserRequestDto request){
        UserResponseDto response = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping ("/refresh")
    public ResponseEntity<UserResponseDto> refresh (@RequestBody String refresh){
        UserResponseDto response = userService.refresh(refresh);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }





}
    
    

