package com.yers4.gestion_tareas.model;



import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.yers4.gestion_tareas.repository.UserRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "tb_users")
@Setter 
@Getter 

@RequiredArgsConstructor  
public class User implements UserDetails{

    private final UserRepository userRepository;


    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = ("id"))
    private  Long id;

    @Column (name = ("password"))
    private String password;

    @Column (name = ("email"))
    private String email;

    @Column (name = ("name"))
    private String name;

    public enum Rol{
        ADMIN,
        USER
    }
    
    @Enumerated (EnumType.STRING)
    private Rol rol;

  @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
}

@Override
    public String getUsername() {
    return this.email; // Indicas que el email se usa como usuario
}

@Override
    public boolean isAccountNonExpired() { return true; }

@Override
    public boolean isAccountNonLocked() { return true; }

@Override
    public boolean isCredentialsNonExpired() { return true; }

@Override
    public boolean isEnabled() { return true; }

@Override 
public String getPassword(){
    return this.password;
}


}







