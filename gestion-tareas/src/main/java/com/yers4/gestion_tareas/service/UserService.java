package com.yers4.gestion_tareas.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yers4.gestion_tareas.dto.UserRequestDto;
import com.yers4.gestion_tareas.dto.UserResponseDto;
import com.yers4.gestion_tareas.model.User;
import com.yers4.gestion_tareas.model.User.Rol;
import com.yers4.gestion_tareas.exception.CredencialesInvalidadasException;
import com.yers4.gestion_tareas.exception.RecursoDuplicadoException;
import com.yers4.gestion_tareas.exception.RecursoNoEncontradoException;
import com.yers4.gestion_tareas.mapper.UserMapper;
import com.yers4.gestion_tareas.repository.UserRepository;
import com.yers4.gestion_tareas.security.JwtService;


import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 

public class UserService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public UserResponseDto registre (UserRequestDto request){

        userRepository.findByEmail(request.getEmail())
        .ifPresent((user) -> {
            throw new RecursoDuplicadoException("el usuario ya se encuentra en la base de datos");
        });

       User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRol(Rol.USER);
        userRepository.save(user);

        String acessToken = jwtService.generarAcessToken(request.getEmail());
        String refreshToken = jwtService.generarRefreshToken(request.getEmail());

        UserResponseDto response = new UserResponseDto();
        response.setAcessToken(acessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    public UserResponseDto login (UserRequestDto request){
        
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RecursoNoEncontradoException("el usuario no exixte en la base de adtos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            throw new CredencialesInvalidadasException("contraseña incorrecta");
            
        }

        String acessToken = jwtService.generarAcessToken(request.getEmail());
        String refreshToken = jwtService.generarRefreshToken(request.getEmail());

        UserResponseDto response = new UserResponseDto();
        response.setAcessToken(acessToken);
        response.setRefreshToken(refreshToken);
        
        return  response;
    }

    public UserResponseDto refresh (String refresh){

        String tipo = jwtService.obtenerTipoToken(refresh);

        if (!"REFRESH".equals(tipo)) {

            throw new IllegalArgumentException("el token o es un refresh");  
        }

        String username = jwtService.extraerUserName(refresh);
        String nuevoAcess = jwtService.generarAcessToken(username);

        UserResponseDto response = new UserResponseDto();
        response.setAcessToken(nuevoAcess);
        response.setRefreshToken(refresh);
        return response;

    }

    
    
}
