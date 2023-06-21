package br.com.marcosvinicios.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcosvinicios.security.entities.Paciente;
import br.com.marcosvinicios.security.repositories.PacienteRepository;

@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> findAll(){
        return pacienteRepository.findAll();
    }

    public void save(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public void delete(Paciente paciente) {
        pacienteRepository.delete(paciente);
    }

}
