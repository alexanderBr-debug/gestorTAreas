package com.yers4.gestion_tareas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yers4.gestion_tareas.dto.UserRequestDto;
import com.yers4.gestion_tareas.dto.UserResponseDto;
import com.yers4.gestion_tareas.mapper.UserMapper;
import com.yers4.gestion_tareas.model.User;
import com.yers4.gestion_tareas.repository.UserRepository;
import com.yers4.gestion_tareas.security.JwtEncoder;
import com.yers4.gestion_tareas.security.JwtService;
import com.yers4.gestion_tareas.service.UserService;

/* habilitamos la integracion de la libreria mockito y lo hacemos para que 
JUnit sepa como procesar los objetos simulados */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /* creamos un objeto falso o simulado de la clase indicada no ejecuta codigo real 
    ya que aislamos de la base de datos */
    @Mock 
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @Mock 
    private JwtEncoder jwtEncoder;

    @Mock 
    private PasswordEncoder passwordEncoder;

    /* creamos o inyectamos una instancia real pero que va a procesar nuestro mock
    osea que simplemente va a procesar datos sin utilzar la base de datos real ya que usara
    la falsa que creamos arita */
    @InjectMocks 
    private UserService userService;

    /* marcamos este metodo para que jUnit lo reconozca como una prueba ejecutable ya que si no lo usamos
    la herramienta de prueba lo ignora */
    @Test 
    void deberiCrearUsuarioCorrectamente(){

        UserRequestDto request = new UserRequestDto();
        request.setName("alex");
        request.setEmail("alex@Email.com");
        request.setPassword("hola");

        // Creamos una entidad simulada para evitar nulos
        User userEntityMock = new User(any());
        userEntityMock.setName("alex");
        userEntityMock.setEmail("alex@Email.com");

        // Simulamos la conversión del DTO a la Entidad
        when(userMapper.toEntity(any(UserRequestDto.class)))
        .thenReturn(userEntityMock);

        when(userRepository.findByEmail("alex@Email.com"))
        .thenReturn(Optional.empty());

        when(passwordEncoder.encode(any()))
        .thenReturn("passWordEncriptado");

        when(jwtService.generarAcessToken(any()))
        .thenReturn("token");

        UserResponseDto response = userService.registre(request);

        assertEquals("token", response.getAcessToken());
        verify(userRepository, times(1)).save(any(User.class));    
    }

    @Test 
    void deberiaLanzarUnaExceptionCuandoEmailExiste(){

        UserRequestDto request = new UserRequestDto();
        request.setName("alex");
        request.setEmail("alex@Email.com");
        request.setPassword("hola");

        when(userRepository.findByEmail("alex@Email.com"))
        .thenReturn(Optional.of(new User(any())));

        assertThrows(RuntimeException.class, () -> {
            userService.registre(request);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test 
    void deberiaAutenticarUSuarioCorrectamente(){

        UserRequestDto request = new UserRequestDto();
        request.setName("alex");
        request.setEmail("alex@email.com");
        request.setPassword("hola");

        when(userRepository.findByEmail("alex@email.com"))
        .thenReturn(Optional.of(new User(any())));

        // Corrección: No se permite el operador ! dentro del when().
        when(passwordEncoder.matches(any(), any()))
        .thenReturn(true);

        when(jwtService.generarRefreshToken(any()))
        .thenReturn("token");

        UserResponseDto response = userService.login(request);

        assertEquals("token", response.getAcessToken());
        verify(userRepository, times(1)).findByEmail("alex@email.com"); 
    }
}