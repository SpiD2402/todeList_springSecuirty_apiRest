package com.example.todelist.service.impl;

import com.example.todelist.contants.Const;
import com.example.todelist.dao.TareaDao;
import com.example.todelist.dao.UsuariosDao;
import com.example.todelist.dto.auth.SignInUsuario;
import com.example.todelist.dto.auth.SignUpUsuario;
import com.example.todelist.dto.passwordChange.PasswordChangeDao;
import com.example.todelist.entity.TareaEntity;
import com.example.todelist.entity.UsuariosEntity;
import com.example.todelist.response.ResponseDatos;
import com.example.todelist.segurity.JwtService;
import com.example.todelist.segurity.JwtUserDetailsService;
import com.example.todelist.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl  implements AuthService {

    private final UsuariosDao usuariosDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TareaDao tareaDao;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtService jwtService;


    public AuthServiceImpl(UsuariosDao usuariosDao, BCryptPasswordEncoder bCryptPasswordEncoder, TareaDao tareaDao, AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService, JwtService jwtService) {
        this.usuariosDao = usuariosDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tareaDao = tareaDao;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtService = jwtService;
    }


    @Override
    public ResponseDatos signIn(SignInUsuario signInUsuario) {
        UserDetails userDetails;
        try {
            userDetails = this.jwtUserDetailsService.loadUserByUsername(signInUsuario.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("El usuario no existe.");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInUsuario.getUsername(), signInUsuario.getPwd())
            );
            final String token = this.jwtService.generateToken(userDetails);
            return new ResponseDatos().succes(Const.OK, Const.OK_MESSAGE, Optional.of(token));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales incorrectas. Por favor, verifica tu nombre de usuario y contraseña.");
        } catch (DisabledException e) {
            throw new DisabledException("La cuenta está deshabilitada. Por favor, contacta al administrador.");
        }
    }

    @Override
    public ResponseDatos signUp(SignUpUsuario signUpUsuario) {
        return null;
    }

    @Override
    public ResponseDatos changePassword(PasswordChangeDao passwordChangeDao) {
        Optional<UsuariosEntity> usuarioEncontrado = usuariosDao.findByUsername(passwordChangeDao.getUsername());
        if (!usuarioEncontrado.isPresent())
        {
            return  new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of("Usuario no existe"));
        }
        else{
                UsuariosEntity usuariosEntity = usuarioEncontrado.get();
                if( !bCryptPasswordEncoder.matches(passwordChangeDao.getCurrentPassword(),usuariosEntity.getPwd()))
                {
                    return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of("La contraseña actual es incorrecta"));
                }
                if (!passwordChangeDao.getNewPassword().equals(passwordChangeDao.getConfirmPassword()))
                {
                    return new ResponseDatos().error(Const.NOT_FOUND,Const.NOT_FOUND_MESSAGE,Optional.of("Las nuevas contraseñas no coinciden"));
                }
                String encodedNewPassword=  bCryptPasswordEncoder.encode(passwordChangeDao.getNewPassword());
                usuariosEntity.setPwd(encodedNewPassword);
                usuariosDao.save(usuariosEntity);
                return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of("La contraseña fue cambiada con exito"));
        }
    }

    @Override
    public ResponseDatos loadByProfile(Authentication authentication) {
        String username   = authentication.getName();
        if (username == null)
        {
            return  new ResponseDatos().error(Const.INTERNAL_SERVER_ERROR,Const.INTERNAL_SERVER_ERROR_MESSAGE,Optional.empty());
        }
        else{
                Optional<UsuariosEntity> usuarioEncontrado = usuariosDao.findByUsername(username);
                Long usuarioId = Long.valueOf(usuarioEncontrado.get().getId());
                List<TareaEntity> tareaEntityList = tareaDao.findAllByUsuarioId(usuarioId);
                if (tareaEntityList.isEmpty())
                {
                    return  new ResponseDatos().succes(Const.NO_CONTENT,Const.NO_CONTENT_MESSAGE,Optional.of(tareaEntityList));
                }

                return new ResponseDatos().succes(Const.OK,Const.OK_MESSAGE,Optional.of(tareaEntityList));
        }
    }
}
