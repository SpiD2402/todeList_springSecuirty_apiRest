package com.example.todelist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class UsuariosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    private String username;
    private String email ;

    @JsonIgnore
    private String pwd;
    public Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns =@JoinColumn(name = "id_usuario"),
            inverseJoinColumns =@JoinColumn(name = "id_role")
    )
    private List<RolesEntity> roles;


    public   UsuariosEntity(){
        this.roles = roles;
    }

}
