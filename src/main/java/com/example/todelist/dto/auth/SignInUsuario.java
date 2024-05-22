package com.example.todelist.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInUsuario {

    @NotBlank
    private String username;

    @NotBlank
    private String pwd;



}
