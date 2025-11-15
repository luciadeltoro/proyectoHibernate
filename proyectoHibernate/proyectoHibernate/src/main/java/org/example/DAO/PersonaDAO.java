package org.example.DAO;

import java.util.List;
import org.example.entities.Persona;

public interface PersonaDAO {
    Persona findById(Integer id);
    Persona findByDni(String dni);
    Persona create(Persona p);
    Persona update(Persona p);
    boolean deleteById(Integer id);
    List<Persona> findAll();
}
