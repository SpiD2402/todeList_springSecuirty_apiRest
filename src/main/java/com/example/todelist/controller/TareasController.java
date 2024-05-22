package com.example.todelist.controller;

import com.example.todelist.contants.Const;
import com.example.todelist.dto.tareas.TareaDto;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarea")
public class TareasController {

    private final TareaService tareaService;


    public TareasController(TareaService tareaService) {
        this.tareaService = tareaService;
    }


    @GetMapping
    public ResponseDatos allTarea()
    {
        return  tareaService.allTarea();
    }

    @DeleteMapping("/{id}")
    public ResponseDatos deleteTarea(@PathVariable Long id)
    {
        return  tareaService.deleteTarea(id);
    }

    @GetMapping("/{id}")
    public ResponseDatos getById(@PathVariable Long id)
    {
        return tareaService.geByIdTarea(id);
    }

    @PostMapping
    public ResponseDatos createTarea(@Valid @RequestBody TareaDto tareaDto , BindingResult result)
    {
        if (result.hasErrors())
        {
            return  new ResponseDatos().error(Const.BAD_REQUEST,Const.BAD_REQUEST_MESSAGE, Optional.of(validar(result).getBody()));
        }
        return  tareaService.addTarea(tareaDto);
    }

    @PatchMapping("/{id}")
    public ResponseDatos updateTarea(@PathVariable Long id,@Valid @RequestBody TareaDto tareaDto,BindingResult result)
    {
        if (result.hasErrors())
        {
            return  new ResponseDatos().error(Const.BAD_REQUEST,Const.BAD_REQUEST_MESSAGE, Optional.of(validar(result).getBody()));
        }
        return tareaService.updateTarea(id,tareaDto);

    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String>errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField()+" "+err.getDefaultMessage());});
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
