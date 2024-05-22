package com.example.todelist.segurity;

import com.example.todelist.dao.UsuariosDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

        private  final UsuariosDao usuariosDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosDao.findByUsername(username).map(
                user ->{
                final var authorities = user.getRoles()
                        .stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());

                return new User(user.getUsername(),user.getPwd(),user.getEnabled(),true,true,true,authorities);
                }

        ).orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));
    }
}
