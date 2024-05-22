package com.example.todelist.dao;

import com.example.todelist.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesDao extends JpaRepository<RolesEntity,Long> {


    Optional<RolesEntity> findByNombre(String nombre);

}
