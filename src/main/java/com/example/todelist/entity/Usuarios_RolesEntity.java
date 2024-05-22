package com.example.todelist.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios_roles")
public class Usuarios_RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuarios_roles;
    private Long id_usuario;
    private Long id_role;


}
