package com.example.todelist.service;

import com.example.todelist.dto.roles.RoleDto;
import com.example.todelist.response.ResponseDatos;

public interface RolesService {

    ResponseDatos allRoles();
    ResponseDatos getByIdRole(Long id);

    ResponseDatos addRoles(RoleDto roleDto);

    ResponseDatos deleteRoles(Long id);

    ResponseDatos updateRoles(Long id,RoleDto roleDto);

}
