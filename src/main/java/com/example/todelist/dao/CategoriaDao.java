package com.example.todelist.dao;

import com.example.todelist.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDao extends JpaRepository<CategoriaEntity,Long> {
}
