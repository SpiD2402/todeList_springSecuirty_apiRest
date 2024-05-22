package com.example.todelist.dto.tareas;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TareaDto {

    private  Long id_usuario;

    @NotNull
    private  Long id_categoria;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    private boolean completada;


}
