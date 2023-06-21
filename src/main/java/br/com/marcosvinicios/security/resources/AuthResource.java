package br.com.marcosvinicios.security.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosvinicios.security.dto.Login;
import br.com.marcosvinicios.security.entities.Usuario;
import br.com.marcosvinicios.security.services.TokenService;

@RestController
public class AuthResource {
    
 @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping( value = "/login" )
    public ResponseEntity<String> login(@RequestBody Login login){
    
        /**
         * Classe que recebe os dados de login
         */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                        new UsernamePasswordAuthenticationToken(login.login(),
                                login.password());
        
        /**
         * Faz a autenticação
         */
        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
             
        /**
         * Retorna o usuário autenticado
         */
        var usuario = (Usuario) authenticate.getPrincipal();

        /**
         * Pega o usuário encontrado no banco, e gera um token a partir dele
         */
        return ResponseEntity.ok().body(tokenService.gerarToken(usuario));
    }

}
