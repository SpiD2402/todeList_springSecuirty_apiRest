package com.example.todelist.service.impl;

import com.example.todelist.contants.Const;
import com.example.todelist.dao.RolesDao;
import com.example.todelist.dto.roles.RoleDto;
import com.example.todelist.entity.RolesEntity;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.service.RolesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {

    private final RolesDao rolesDao;

    public RolesServiceImpl(RolesDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    @Override
    public ResponseDatos allRoles() {
        List<RolesEntity> rolesEntities = rolesDao.findAll();
        if (rolesEntities.isEmpty())
        {
            return  new ResponseDatos().error(Const.NO_CONTENT,Const.NO_CONTENT_MESSAGE, Optional.of(List.of()));
        }

        return  new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE, Optional.of(rolesEntities));
    }

    @Override
    public ResponseDatos getByIdRole(Long id) {
        Optional<RolesEntity>rolesEntity = rolesDao.findById(id);
        if (rolesEntity.isEmpty())
        {
            return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,rolesEntity);

        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));
    }

    @Override
    public ResponseDatos addRoles(RoleDto roleDto) {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setNombre(roleDto.getNombre());
        rolesDao.save(rolesEntity);
        return new ResponseDatos().succes(Const.CREATED,Const.CREATED_MESSAGE,Optional.of(rolesEntity)) ;
    }

    @Override
    public ResponseDatos deleteRoles(Long id) {
        Optional<RolesEntity>rolEncontrado = rolesDao.findById(id);
        if (rolEncontrado.isEmpty())
        {
            RolesEntity rolesEntity = rolEncontrado.get();
            rolesDao.delete(rolesEntity);
            return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of("Eliminacion exitosa"));

        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));
    }

    @Override
    public ResponseDatos updateRoles(Long id,RoleDto roleDto) {
        Optional<RolesEntity>rolEncontrado = rolesDao.findById(id);
        if (rolEncontrado.isPresent())
        {
            RolesEntity rolesEntity  = new RolesEntity();
            rolesEntity.setNombre(roleDto.getNombre());
            rolesDao.save(rolesEntity);
            return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of("Role Actualizado"));

        }
        return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of(List.of()));
    }
}
