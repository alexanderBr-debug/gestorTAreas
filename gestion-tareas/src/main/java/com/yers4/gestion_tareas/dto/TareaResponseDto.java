package com.yers4.gestion_tareas.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class TareaResponseDto {

    private Long id;
    private String nameUser;
    private String titulo;
    private String descripcion;
    private LocalDate fechaLimite;
    private Boolean completada;
}
