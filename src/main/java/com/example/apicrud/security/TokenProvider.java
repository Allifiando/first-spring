package com.example.apicrud.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenProvider {
    private String secret = "TUlJQ1hBSUJBQUtCZ1FDeldERmlGcVE3ZGc1NzdNdWVmbXQxb0hicThXOCt0VFYvcFpQVDk1OXczRkl2ZkpZWAovejI3K3ltbWZtOEprV3lNelZwRCtiNnpJdmhjYXdDbUZvekVvOWgvc3o4ZHhVdC95L1VhU2w1TUhIblZjVTJ0CkpUbnN6OFNBY1BvT2RuYy9WWDBpeU84VUlDbkozWUNJOWE2MGlhdjJWMmVRQ2F1bE9ENHFWNHZFVHdJREFRQUIKQW9HQkFJZFY0eFlnL2RmOUU1c0NxdmswYndUNWpTTm9BOG12VnVxM1dTR1llQkhqd0lVakgrU0Y4T0VjL0dZdQpmTDRjcG14dVBsS1RjUXVmTlFvUW1SRzhiSVpuckhiWkcvQXpsY1ZBZkN5WlVrWThqY0Rrd3JKdEtueXRCcFFqCnNHLzc0VS9SdDYyOEtRcjBYM2tCakhvS05nblRyVTZCWmRaN2VOTFFocll3UGI3SkFrRUEyYUgzRGdjRWowMHIKMjNrbXEycENQdUV6c0tLYkI1ckt2QmdkQVdxR1c5V3BCS3NzUTZJZ3B4ekNvR3VXMFFpN0NXN1BlUmpvYkJ6TgpxRlBKYnQramJRSkJBTkwyUEJhYXg3czJ5d3d0QkJSUnFBd3h4aVUzbXNEWEFNamN4dHBQb1dKUUVnYmgrMDMzClBVQVJEYmJWZnRNTlRtRlZ2bnR2ajlmK04ycllMZ0prOVNzQ1FDdFRjaktwdFArdVZsZllFNW0yaXIrbjU3bDMKZGJPYTNsZDUyWFJwdDV2YXVrNUNvWXBKWVlURFZmL0h5M3VMNkdobFZncCtxZTYwQVVGTWhPSjh4VFVDUUJGTwpJWis1RXFsSTFWOHFVaXRZaDNCNHBNaG94MGtLV2dZZ0ZpL1NXR3E0SnNKcHh6T2VGR2dzL2ZNQmtHVy9zYTVLCldsWEYwUWJtMDZUd0Z0WWJjZ0VDUUFUUGMxZFNlQTRRMytCZllSeVFjYmR5ZndFeSsrTnNpb1dZVEliaTJQMDAKZWxFUXoyc3BDbzNvS2ZRaUwzRGRrVnpVSk5oeVNyVG1VdlFiNDdzc1ZSYz0=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        // here it will check if the token has created before time limit. i.e 10 hours then will will return true else false
        return extractExpiration(token).before(new Date());
    }

    // this method is for generating token. as argument is username. so as user first time send request with usernamr and password
    // so here we will fetch the username , so based on that username we are going to create one token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // in this method createToken subject argument is username
    // here we are setting the time for 10 hours to expire the token.
    // and you can see we are using HS256 algorithmn
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    // here we are validation the token
    public Boolean validateToken(String token, UserDetails userDetails) {
        // basically token will be generated in encrpted string and from that string . we extract our usename and password using extractUsername method
        final String username = extractUsername(token);
        // here we are validation the username and then check the token is expired or not
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
