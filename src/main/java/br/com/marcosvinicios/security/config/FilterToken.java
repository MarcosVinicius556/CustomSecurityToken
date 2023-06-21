package br.com.marcosvinicios.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.marcosvinicios.security.repositories.UsuarioRepository;
import br.com.marcosvinicios.security.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Esta classe extende de "OncePerRequestFilter"
 * pois toda vez que for feita uma requisição ela
 * será chamada antes dos demais filtros do spring
 */
@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token;

        /**
         * Aqui estamos pegando a header da requisição,
         * onde vamos poder encontrar o token fornecido 
         * anteriormente
         */
        var authorization = request.getHeader("Authorization");

        if(authorization != null){
            /**
             * Aqui estamos retirando o token do cabeçalho,
             * removendo o prefixo "Bearer "
             * EX: "Bearer 1A2B3BCAS93J48DFJD983J..."
             */

            token = authorization.replace("Bearer ", "");

            /**
             * Pegando o subject a partir do token fornecido
             */
            var subject = this.tokenService.getSubject(token);

            /**
             * Carregando o usuário a partir do subject retornado pelo token
             */
            var usuario = this.usuarioRepository.findByUsuId(subject);

            /**
             * criando a variável de autenticação
             */
            var authentication = new UsernamePasswordAuthenticationToken(usuario, 
                                        null, usuario.getAuthorities());

            /**
             * Depois de verificado e validado, é setado a autenticação para o 
             * Spring saber que pode liberar as rotas para este usuário
             */
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        /**
         * Libera os próximos filtros
         */
        filterChain.doFilter(request, response);
    }    
}
