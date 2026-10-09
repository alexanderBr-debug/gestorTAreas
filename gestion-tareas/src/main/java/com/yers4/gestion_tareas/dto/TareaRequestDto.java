package com.yers4.gestion_tareas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class TareaRequestDto {
    
    @NotBlank (message = "id obligatorio")
    private Long idUsuario;

    @NotBlank (message = "ingrese un titulo")
    private String titulo;

    

    private LocalDate fechaLimite;

    private String descripcion;


}
