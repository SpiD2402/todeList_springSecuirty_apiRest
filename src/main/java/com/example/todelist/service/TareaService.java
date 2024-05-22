package com.example.todelist.service;

import com.example.todelist.dto.tareas.TareaDto;
import com.example.todelist.response.ResponseDatos;
import org.springframework.security.core.Authentication;


public interface TareaService {

    ResponseDatos allTarea();

    ResponseDatos deleteTarea(Long id);

    ResponseDatos addTarea(TareaDto tareaDto);

    ResponseDatos updateTarea(Long id,TareaDto tareaDto);

    ResponseDatos geByIdTarea(Long id);

}
