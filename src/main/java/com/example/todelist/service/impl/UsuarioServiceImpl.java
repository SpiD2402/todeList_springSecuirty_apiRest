package com.example.todelist.service.impl;

import com.example.todelist.contants.Const;
import com.example.todelist.dao.UsuariosDao;
import com.example.todelist.dao.Usuarios_RolesDao;
import com.example.todelist.dto.usuarios.UsuarioDto;
import com.example.todelist.entity.UsuariosEntity;
import com.example.todelist.entity.Usuarios_RolesEntity;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuariosDao usuariosDao;
    private final Usuarios_RolesDao usuariosRolesDao;

    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioServiceImpl(UsuariosDao usuariosDao, Usuarios_RolesDao usuariosRolesDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuariosDao = usuariosDao;
        this.usuariosRolesDao = usuariosRolesDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public ResponseDatos allUsuarios() {
        List<UsuariosEntity> usuariosEntities = usuariosDao.findAll()
                .stream()
                .filter( usuario ->usuario.getEnabled())
                .collect(Collectors.toList());
        if (usuariosEntities.isEmpty())
        {
            return  new ResponseDatos().error(Const.NO_CONTENT,Const.NO_CONTENT_MESSAGE,Optional.of(List.of()));
        }

        return  new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE, Optional.of(usuariosEntities));
    }

    @Override
    public ResponseDatos addUsuarios(UsuarioDto usuarioDto) {
        try{
            UsuariosEntity usuariosEntity = getUsuario(new UsuariosEntity(),usuarioDto);
            usuariosEntity = usuariosDao.save(usuariosEntity);

            Usuarios_RolesEntity usuariosRolesEntity = new Usuarios_RolesEntity();
            usuariosRolesEntity.setId_usuario(usuariosEntity.getId());
            usuariosRolesEntity.setId_role(2L);
            usuariosRolesDao.save(usuariosRolesEntity);
            return new ResponseDatos().succes(Const.CREATED,Const.CREATED_MESSAGE,Optional.of(usuariosEntity)) ;
        }
        catch (Exception e)
        {
                    return new ResponseDatos().error(Const.INTERNAL_SERVER_ERROR,Const.INTERNAL_SERVER_ERROR_MESSAGE,Optional.of(e));
        }


    }

    @Override
    public ResponseDatos findByUsuario(Long id) {
        try{
            Optional<UsuariosEntity> usuarioEncontrado = usuariosDao.findById(id);
            if (usuarioEncontrado.isPresent())
            {
                return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,usuarioEncontrado);
            }
            return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));
        }
        catch (Exception e)
        {
            return new ResponseDatos().error(Const.INTERNAL_SERVER_ERROR,Const.INTERNAL_SERVER_ERROR_MESSAGE,Optional.of(e));
        }

    }

    @Override
    public ResponseDatos updateUsuario(Long id, UsuarioDto usuarioDto) {
        Optional<UsuariosEntity> usuarioEncontrado = usuariosDao.findById(id);
        if (usuarioEncontrado.isPresent())
        {
            UsuariosEntity usuariosEntity = new UsuariosEntity();
            usuariosEntity.setEmail(usuarioDto.getEmail());
            usuariosDao.save(usuariosEntity);
            return new ResponseDatos().succes(Const.OK,"Recurso actualizado exitosamente",Optional.of(usuariosEntity));

        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));

    }

    @Override
    public ResponseDatos deleteUduario(Long id) {
        Optional<UsuariosEntity> usuarioEncontrado = usuariosDao.findById(id);
        if (usuarioEncontrado.isPresent())
        {
            UsuariosEntity usuariosEntity= usuarioEncontrado.get();
            usuariosEntity.setEnabled(false);
            usuariosDao.save(usuariosEntity);
            return  new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of("Se inhabilido el Usuario Exitosamente"));
        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));
    }

    @Override
    public Page<UsuariosEntity> pageUsuarios() {
        final Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC,"id"));
        return  usuariosDao.findAll(pageable);
    }


    public UsuariosEntity getUsuario(UsuariosEntity usuariosEntity, UsuarioDto usuarioDto)
    {
        String passwordEncript = bCryptPasswordEncoder.encode(usuarioDto.getPwd());
        usuariosEntity.setEmail(usuarioDto.getEmail());
        usuariosEntity.setPwd(passwordEncript);
        usuariosEntity.setUsername(usuarioDto.getUsername());
        usuariosEntity.setEnabled(true);


        return  usuariosEntity;
    }


}
