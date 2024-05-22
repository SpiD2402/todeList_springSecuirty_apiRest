package com.example.todelist.service;

import com.example.todelist.dto.usuarios.UsuarioDto;
import com.example.todelist.entity.UsuariosEntity;
import com.example.todelist.response.ResponseDatos;
import org.springframework.data.domain.Page;

public interface UsuarioService {

    ResponseDatos allUsuarios();

    ResponseDatos addUsuarios(UsuarioDto usuarioDto);

    ResponseDatos findByUsuario(Long id);

    ResponseDatos updateUsuario(Long id,UsuarioDto usuarioDto);

    ResponseDatos deleteUduario(Long id);
    Page<UsuariosEntity> pageUsuarios();

}
