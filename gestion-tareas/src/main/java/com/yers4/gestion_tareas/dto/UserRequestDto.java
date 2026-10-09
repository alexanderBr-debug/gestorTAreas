package com.yers4.gestion_tareas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class UserRequestDto {
    
    @NotBlank (message = "nombre obligatorio")
    @Size (min = 4,max = 20,message = "debe estar en el  rango")
    private String name;

    @Email (message = "debe tener formato email")
    private String email;

    @NotBlank (message = "contraseña obligatoria")
    private String password;
}
