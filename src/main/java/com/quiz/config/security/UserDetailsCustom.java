package com.quiz.config.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsCustom extends UserDetails {

   String getNome();

   boolean isAdmin();

}
