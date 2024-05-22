package com.example.todelist.service;

import com.example.todelist.dto.auth.SignInUsuario;
import com.example.todelist.dto.auth.SignUpUsuario;
import com.example.todelist.dto.passwordChange.PasswordChangeDao;
import com.example.todelist.response.ResponseDatos;
import org.springframework.security.core.Authentication;


public interface AuthService {

    ResponseDatos signIn(SignInUsuario signInUsuario);

    ResponseDatos signUp(SignUpUsuario signUpUsuario);

    ResponseDatos changePassword(PasswordChangeDao passwordChangeDao);

    ResponseDatos loadByProfile(Authentication authentication);

}
