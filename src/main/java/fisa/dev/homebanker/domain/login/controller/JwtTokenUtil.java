package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenUtil {

  @Value("${spring.jwt.secret}")
  private String secretKey;

  private String createToken(Map<String, Object> claims) {
    String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
    byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    return Jwts.builder()
        .signWith(key)
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 하루동안 유지
        .compact();
  }

  public String generateCustomerToken(Customer customer) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("CustomerLoginId", customer.getCustomerLoginId());
    claims.put("CustomerRole", customer.getCustomerRole());
    return createToken(claims);
  }

  public String generateEmployeeToken(Employee employee) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("EmployeeLoginId", employee.getEmployeeLoginId());
    claims.put("EmployeeRole", employee.getEmployeeRole());
    return createToken(claims);
  }

  private Claims extractAllClaims(String token) {
    if (StringUtils.isEmpty(token)) {
      return null;
    }
    String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
    Claims claims = null;
    try {
      claims = Jwts.parserBuilder().setSigningKey(secretKeyEncodeBase64).build()
          .parseClaimsJws(token).getBody();
    } catch (JwtException e) {
      claims = null;
    }
    return claims;
  }

  public String extractCustomerName(String token) {
    final Claims claims = extractAllClaims(token);
    if (claims == null) {
      return null;
    } else {
      return claims.get("CustomerLoginId", String.class);
    }
  }

  public String extractCustomerRole(String token) {
    final Claims claims = extractAllClaims(token);
    if (claims == null) {
      return null;
    } else {
      return claims.get("CustomerRole", String.class);
    }
  }

  public String extractEmployeeName(String token) {
    final Claims claims = extractAllClaims(token);
    if (claims == null) {
      return null;
    } else {
      return claims.get("EmployeeLoginId", String.class);
    }
  }

  public String extractEmployeeRole(String token) {
    final Claims claims = extractAllClaims(token);
    if (claims == null) {
      return null;
    } else {
      return claims.get("EmployeeRole", String.class);
    }
  }

}