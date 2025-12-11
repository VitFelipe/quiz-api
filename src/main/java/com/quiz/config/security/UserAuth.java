package com.quiz.config.security;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.quiz.enums.PerfilEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.quiz.model.Usuario;

public class UserAuth  implements UserDetailsCustom {
    private final Usuario user;

    public UserAuth(Usuario user) {
        this.user = user;
    }

    @Override
    public String getNome() {
        return user.getNome();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(user.getPerfil().name()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getSenha();
    }

    @Override
    public String getUsername() {
        return  user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAdmin() {
        if(getAuthorities() == null || getAuthorities().isEmpty()){
            return false;
        }
        return getAuthorities().stream().
                anyMatch(authority -> authority.getAuthority().equals(PerfilEnum.ADMIN.name()));


    }
}
