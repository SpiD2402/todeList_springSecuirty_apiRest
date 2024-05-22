package com.example.todelist.dao;

import com.example.todelist.entity.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TareaDao  extends JpaRepository<TareaEntity,Long> {

    @Query("SELECT t FROM TareaEntity t WHERE t.id_usuario = ?1")
    List<TareaEntity> findAllByUsuarioId(Long usuarioId);


    @Query("SELECT t FROM TareaEntity t WHERE t.id_categoria = ?1")
    List<TareaEntity> findAllByCategoriaId(Long categoriaId);

}
