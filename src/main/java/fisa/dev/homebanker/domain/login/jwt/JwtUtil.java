package fisa.dev.homebanker.domain.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private Key key;

  public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
    byte[] byteSecretKey = Decoders.BASE64.decode(secret);
    key = Keys.hmacShaKeyFor(byteSecretKey);
  }

  public String getLoginId(String token) {

    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
        .get("loginId", String.class);
  }

  public String getRole(String token) {

    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
        .get("role", String.class);
  }

  public Boolean isExpired(String token) {

    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
        .getExpiration().before(new Date());
  }

  public String createJwt(String loginId, String role, Long expiredMs) {
    Claims claims = Jwts.claims();
    claims.put("loginId", loginId);
    claims.put("role", role);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

}