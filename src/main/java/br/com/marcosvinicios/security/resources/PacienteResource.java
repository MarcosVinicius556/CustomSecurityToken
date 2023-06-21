package br.com.marcosvinicios.security.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosvinicios.security.entities.Paciente;
import br.com.marcosvinicios.security.services.PacienteService;

@RestController
@RequestMapping( value = "/paciente" )
public class PacienteResource {
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> findAllPacientes(){
        return ResponseEntity.ok().body(pacienteService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> insertPaciente(@RequestBody Paciente paciente){
        pacienteService.save(paciente);
        return ResponseEntity.ok().body("Paciente " + paciente.getNome() + " inserido com sucesso!");
    }

    @DeleteMapping
    public ResponseEntity<String> removePaciente(@RequestBody Paciente paciente){
        pacienteService.delete(paciente);
        return ResponseEntity.ok().body("Paciente " + paciente.getNome() + " removido com sucesso!");
    }

}
