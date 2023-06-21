package br.com.marcosvinicios.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marcosvinicios.security.entities.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
}
