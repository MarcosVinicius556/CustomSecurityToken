package br.com.marcosvinicios.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    private FilterToken filter;

    /**
     * @apiNote Quando o "AutenticationManager" for chamado na classe service de autenticação, ele irá buscar aqui o objeto
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * @apiNote Faz o encode da senha para verificar o login
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @apiNote Método que fica responsável por controlar as rotas, <br>
     *          configurar cors da aplicação, csrf, verificar quando <br>
     *          a autenticação do usuário é necessária entre outras coisas.
     * @param httpSecurity
     * @return SecurityFilterChain filter
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .cors(cors -> cors.disable()) //Desabilitando CORS 
        .csrf(csrf -> csrf.disable()) //Desabilitando CSRF
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Definindo a API como STATELESS
        .authorizeHttpRequests(auth -> { //Configurando Rotas
            auth.requestMatchers(HttpMethod.POST, "/login").permitAll(); //Rota pública
            auth.requestMatchers(HttpMethod.POST, "/logout").permitAll(); //Rota pública
            auth.anyRequest().authenticated(); //Definindo que todas as demais rotas deverão ser autenticadas
        })
        //Este filtro virá antes dos demais, e é onde iremos validar o token que foi gerado para poder acessar as demais rotas
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class) 
        .build();
    }

}
