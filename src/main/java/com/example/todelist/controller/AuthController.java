package com.example.todelist.controller;

import com.example.todelist.dto.auth.SignInUsuario;
import com.example.todelist.dto.passwordChange.PasswordChangeDao;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sigIn")
    private ResponseDatos SigIn(@RequestBody SignInUsuario signInUsuario)
    {
        return authService.signIn(signInUsuario);
    }

    @PostMapping("/change")
    private ResponseDatos ChangePassword(@RequestBody PasswordChangeDao passwordChangeDao)
    {
        return authService.changePassword(passwordChangeDao);
    }

    @GetMapping
    public ResponseDatos loadByProfile(Authentication authentication)
    {
        return  authService.loadByProfile(authentication);
    }




}
