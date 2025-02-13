package com.example.todelist.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUsuario {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email ;

    @NotBlank
    private String pwd;
}
