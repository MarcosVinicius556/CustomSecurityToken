package br.com.marcosvinicios.security.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.marcosvinicios.security.entities.Usuario;

@Service
public class TokenService {
    
    /**
     * @apiNote Método responsável por criar um token
     *          personalizado a partir dos dados do usuário que se logou 
     * @param usuario
     * @return String token de autenticação
     */
    public String gerarToken(Usuario usuario){
        return JWT.create()
                  .withIssuer("Pacientes")
                  .withSubject(usuario.getUsername())
                  .withClaim("id", usuario.getUsuId())
                  .withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")))
                  .sign(Algorithm.HMAC256("secret_word"));
    }

}
