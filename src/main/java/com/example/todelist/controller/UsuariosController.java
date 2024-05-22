package com.example.todelist.controller;

import com.example.todelist.contants.Const;
import com.example.todelist.dto.usuarios.UsuarioDto;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("api/usuario")
public class UsuariosController {

    private final UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseDatos allUsuarios()
    {

            return usuarioService.allUsuarios();


    }
    @GetMapping("/page")
    public Page pageUsuarios()
    {
        return usuarioService.pageUsuarios();
    }
    @PostMapping
    public  ResponseDatos addUsuario(@Valid @RequestBody UsuarioDto usuarioDto ,BindingResult result)
    {
        if (result.hasErrors())
        {
            return  new ResponseDatos().error(Const.BAD_REQUEST,Const.BAD_REQUEST_MESSAGE, Optional.of(validar(result).getBody()));
        }
        return usuarioService.addUsuarios(usuarioDto);
    }

    @GetMapping("/{id}")
    public ResponseDatos findByUsuario(@PathVariable Long id)
    {
        return usuarioService.findByUsuario(id);
    }

    @PatchMapping("/{id}")
    public  ResponseDatos updateUsuario(@PathVariable Long id ,@Valid @RequestBody UsuarioDto usuarioDto,BindingResult result)
    {
        if (result.hasErrors())
        {
            return  new ResponseDatos().error(Const.BAD_REQUEST,Const.BAD_REQUEST_MESSAGE, Optional.of(validar(result).getBody()));
        }
        return usuarioService.updateUsuario(id,usuarioDto);
    }


    @DeleteMapping("/{id}")
    public ResponseDatos deleteUsuario(@PathVariable Long id)
    {
        return  usuarioService.deleteUduario(id);
    }



    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String>errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField()+" "+err.getDefaultMessage());});
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
