package com.example.todelist.dao;

import com.example.todelist.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosDao extends JpaRepository<UsuariosEntity,Long> {


    Optional<UsuariosEntity> findByUsername(String username);

}
