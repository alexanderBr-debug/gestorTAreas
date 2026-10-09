package com.yers4.gestion_tareas.dto;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class UserResponseDto {
    private String acessToken;
    private String refreshToken;
}
