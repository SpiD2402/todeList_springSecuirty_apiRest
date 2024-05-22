package com.example.todelist.exceptionHandler;

import com.example.todelist.contants.Const;
import com.example.todelist.response.ResponseDatos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class AccessDeniedExceptionHandler{


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new ResponseDatos().error(Const.NOT_FOUND, "El usuario no existe.", Optional.of(e.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new ResponseDatos().error(Const.NOT_FOUND, "Credenciales incorrectas. Por favor, verifica tu nombre de usuario y contraseña.", Optional.of(List.of())), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledException(DisabledException e) {
        return new ResponseEntity<>(new ResponseDatos().error(Const.NOT_FOUND, "La cuenta está deshabilitada. Por favor, contacta al administrador.", Optional.of(e.getMessage())), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(new ResponseDatos().error(Const.NOT_FOUND, "Ocurrió un error interno.", Optional.of(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
