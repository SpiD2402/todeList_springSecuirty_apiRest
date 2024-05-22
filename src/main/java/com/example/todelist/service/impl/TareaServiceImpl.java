package com.example.todelist.service.impl;

import com.example.todelist.contants.Const;
import com.example.todelist.dao.TareaDao;
import com.example.todelist.dao.UsuariosDao;
import com.example.todelist.dto.tareas.TareaDto;
import com.example.todelist.entity.TareaEntity;
import com.example.todelist.entity.UsuariosEntity;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.service.TareaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaDao tareaDao;
    private final UsuariosDao usuariosDao;

    public TareaServiceImpl(TareaDao tareaDao, UsuariosDao usuariosDao) {
        this.tareaDao = tareaDao;
        this.usuariosDao = usuariosDao;
    }

    @Override
    public ResponseDatos allTarea() {
        List<TareaEntity> tareaEntityList = tareaDao.findAll().stream().filter(tareaEntity -> tareaEntity.isStatus()).collect(Collectors.toList());
        if (tareaEntityList.isEmpty())
        {
            return  new ResponseDatos().error(Const.NO_CONTENT,Const.NO_CONTENT_MESSAGE,Optional.of(List.of()));

        }
        return  new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE, Optional.of(tareaEntityList));
    }


    @Override
    public ResponseDatos deleteTarea(Long id) {
        Optional<TareaEntity>tareaEncontrada = tareaDao.findById(id);
        if (tareaEncontrada.isPresent())
        {
            TareaEntity tareaEntity = tareaEncontrada.get();
            tareaEntity.setStatus(false);
            tareaDao.save(tareaEntity);
            return  new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of("Se inhabilido la Tarea Exitosamente"));

        }

        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));

    }

    @Override
    public ResponseDatos addTarea(TareaDto tareaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username != null)
        {
            Optional<UsuariosEntity> usuariosEntity = usuariosDao.findByUsername(username);

            TareaEntity tareaEntity = getTarea(new TareaEntity(),tareaDto);
            tareaEntity.setId_usuario(usuariosEntity.get().getId());
            tareaEntity.setUsuario_creacion(username);
            tareaDao.save(tareaEntity);
            return new ResponseDatos().succes(Const.CREATED,Const.CREATED_MESSAGE,Optional.of(tareaEntity));
        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.empty());

    }

    @Override
    public ResponseDatos updateTarea(Long id, TareaDto tareaDto) {
        Optional<TareaEntity>tareaEncotrada= tareaDao.findById(id);
        if (tareaEncotrada.isPresent())
        {
                TareaEntity tareaEntity = tareaEncotrada.get();
                tareaEntity.setTitulo(tareaDto.getTitulo());
                tareaEntity.setDescripcion(tareaDto.getDescripcion());
                tareaEntity.setCompletada(tareaDto.isCompletada());
                tareaEntity.setId_categoria(tareaDto.getId_categoria());
                tareaEntity.setId_usuario(tareaDto.getId_usuario());
                tareaDao.save(tareaEntity);
                return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of("Se actualizo exitosamente"));
        }

        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));

    }

    @Override
    public ResponseDatos geByIdTarea(Long id) {
        Optional<TareaEntity> tareaEntity = tareaDao.findById(id);
        if (tareaEntity.isPresent())
        {
            return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,tareaEntity);

        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));
    }


    public Timestamp getTime ()
    {
        Long current = System.currentTimeMillis();
        return  new Timestamp(current);
    }


    public TareaEntity getTarea(TareaEntity tareaEntity,TareaDto tareaDto)
    {
        tareaEntity.setId_categoria(tareaDto.getId_categoria());
        tareaEntity.setStatus(true);
        tareaEntity.setCompletada(false);
        tareaEntity.setDescripcion(tareaDto.getDescripcion());
        tareaEntity.setTitulo(tareaDto.getTitulo());
        tareaEntity.setFecha_creacion(getTime());
        return  tareaEntity;
    }

}
