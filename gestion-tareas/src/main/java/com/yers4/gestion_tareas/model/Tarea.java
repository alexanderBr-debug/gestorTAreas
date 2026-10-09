package com.yers4.gestion_tareas.model;


import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "tb_tarea")
@Setter 
@Getter 
@NoArgsConstructor 
public class Tarea {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = ("id"))
    private Long id;

    @Column (name = ("titulo"))
    private String titulo;

    @Column (name = ("completada"))
    private boolean completada;

    @Column (name = ("descripcion"))
    private String descripcion;

    @Column (name = ("fechaLimite"))
    private LocalDate fechaLimite;

    @ManyToOne 
    @JoinColumn (name = ("user_id"))
    private User user;
    
}
