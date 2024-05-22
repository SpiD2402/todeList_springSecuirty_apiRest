package com.example.todelist.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "tareas")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  Long id_usuario;

    private  Long id_categoria;

    private String titulo;

    private String descripcion;

    private  boolean status;
    private boolean completada;

    private Timestamp fecha_creacion;

    private Timestamp fecha_eliminacion;
    private Timestamp fecha_actualizacion;

    private String usuario_creacion;

    private String usuario_actualizacion;


}
