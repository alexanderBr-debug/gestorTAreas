package com.yers4.gestion_tareas.security;

import java.security.Key;
import java.util.Date;


import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class JwtService {

    private final JwtProperties jwtProperties;
    

    public String generarAcessToken(String email){

        
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        //inicimos la constraccion del token con builer
        return  Jwts.builder()
        .setSubject(email)
        .claim("tipo_token", "ACESS")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
        .signWith(key,SignatureAlgorithm.HS256)
        .compact();
    }


    public String generarRefreshToken(String email){

        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        return Jwts.builder()
        .setSubject(email)
        .claim("tipo_token", "REFRESH")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 + 60 * 60 * 24 * 7))
        .signWith(key,SignatureAlgorithm.HS256)
        .compact();
    }

    private Claims extraerClaims(String token){
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        return Jwts.parserBuilder()
        //comparamos si tiene nuestra firma
            .setSigningKey(key)
            .build()
            //compara el tken pasado con el nuevo
            .parseClaimsJws(token)
            //si todo esta bien extraemos los datos del cuerpo
            .getBody();
    }

    public String obtenerTipoToken(String token){
         Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .get("tipo_token",String.class);
    }

    public String extraerUserName(String token){
        return extraerClaims(token).getSubject();
    }

    public String extraerEmail(String token){
        return extraerClaims(token).getSubject();
    }

    public boolean elTokenEsValido(String token,String emailEsperado){
        try{
            Claims claims = extraerClaims(token);
            String emailDelToken = claims.getSubject();
            boolean noEstaExpirado = !claims.getExpiration().before(new Date());
            return emailDelToken.equals(emailEsperado) && noEstaExpirado;
        }
        catch (Exception e){
            return false;
        }

    }
    
}
