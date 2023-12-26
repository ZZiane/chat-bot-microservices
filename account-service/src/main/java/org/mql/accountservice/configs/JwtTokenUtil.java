package org.mql.accountservice.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
public class JwtTokenUtil implements Serializable {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "ZACH";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";



    Date now =new Date();
    Date ExpirationDate =new Date(now.getTime()+ ACCESS_TOKEN_VALIDITY_SECONDS*1000);

    public String getEmail(final String token) {
        Claims body = Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }

    public String generateToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration( ExpirationDate)
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();

    }

    public boolean validateToken(final String token) {
        return getClaims(SIGNING_KEY,token)
                .getExpiration()
                .after(new Date (System.currentTimeMillis()));
    }

    public Claims getClaims(String secret ,String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}