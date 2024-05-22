package com.example.todelist.dao;

import com.example.todelist.entity.Usuarios_RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Usuarios_RolesDao  extends JpaRepository<Usuarios_RolesEntity,Long> {
}
