package br.com.marcosvinicios.security.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @apiNote Para fazer a autenticação com Spring Security <br>
 *          é necessário que a classe model de login <br>
 *          (Neste caso "Usuario") implemente  a interface <br>
 *          "UserDetails", pois é a partir dela que o <br>
 *          spring vai conseguir fazer o controle e acesso <br>
 *          de dados como userName ou rotas disponíveis por exemplo
 */

@Entity
@AllArgsConstructor
@ToString
public class Usuario implements UserDetails {
    
    @Id
    @Getter
    @Setter
    @Column( name = "usuid" )
    private String usuId;

    @Getter
    @Setter
    @Column( name = "usudesc" )
	private String usuDesc;

    @Getter
    @Setter
    @Column( name = "senha" )
	private String senha;

    public Usuario() {
        this.usuId = "";
        this.usuDesc = "";
        this.senha = "";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((usuId == null) ? 0 : usuId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (usuId == null) {
            if (other.usuId != null)
                return false;
        } else if (!usuId.equals(other.usuId))
            return false;
        return true;
    }

    /**
     * Autorizações, para distinguir o tipo de usuário por exemplo (ex: "ADM", "FUNC")
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return usuId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
