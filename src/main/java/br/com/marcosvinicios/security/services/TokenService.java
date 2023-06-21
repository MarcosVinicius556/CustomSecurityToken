package br.com.marcosvinicios.security.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.marcosvinicios.security.entities.Usuario;

@Service
public class TokenService {
    
    private static final String ISSUER = "Pacientes";
    private static final String SECRET_WORD = "secret_word";

    /**
     * @apiNote Método responsável por criar um token
     *          personalizado a partir dos dados do usuário que se logou 
     * @param usuario
     * @return String token de autenticação
     */
    public String gerarToken(Usuario usuario){
        return JWT.create()
                  .withIssuer(ISSUER)
                  .withSubject(usuario.getUsername())
                  .withClaim("id", usuario.getUsuId())
                  .withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")))
                  .sign(Algorithm.HMAC256(SECRET_WORD));
    }

    /**
     * @apiNote Este método pega o token, verifica se está correto e válido 
     *          e retorna o subject dele
     * @param token
     * @return String subject
     */
    public String getSubject(String token) {
        //Utiliza o mesmo algorítimo da criação e a mesma palavra secreta para poder verificar o JWT
        return JWT.require(Algorithm.HMAC256(SECRET_WORD)) 
                  .withIssuer(ISSUER)  //Mesmo Issuer de geração do token
                  .build() //Monta o JWT
                  .verify(token) //Verifica a validade
                  .getSubject(); //Retorna o subject do token
    }

}
