package br.com.marcosvinicios.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.marcosvinicios.security.repositories.UsuarioRepository;

/**
 * Esta classe é o serviço que vai atender o chamado da autenticação,
 * então obrigatoriamente ela precisará implementar a interface "UserDetailsService" 
 * para poder encontrar um usuário pelos dados inseridos
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Buscar um usuário no banco pelo seu login (Neste caso utilizando o usuID)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsuId(username);
    }

    
}
