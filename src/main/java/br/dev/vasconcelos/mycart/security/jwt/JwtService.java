package br.dev.vasconcelos.mycart.security.jwt;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.token-expiration}")
    private String expiration;

    @Value("${security.jwt.signing-key}")
    private String signingKey;

    public String tokenGenerate(UserProfile user){
        long expString = Long.valueOf(expiration);
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValidate(String token){
        try{
            Claims claims = getClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e){
            return false;
        }
    }

    public String getUserEmail(String token) throws ExpiredJwtException {
        return (String)  getClaims(token).getSubject();
    }

//    public static void main(String[] args){
//        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
//        JwtService service = context.getBean(JwtService.class);
//        Usuario usuario = Usuario.builder().login("andreFilho").build();
//        String token = service.gerarToken(usuario);
//        System.out.println(token);
//
//        boolean isTokenValido = service.tokenValido(token);
//        System.out.println("Token válido: " + isTokenValido);
//
//        System.out.println(service.obterLoginUsuario(token));
//    }
}
