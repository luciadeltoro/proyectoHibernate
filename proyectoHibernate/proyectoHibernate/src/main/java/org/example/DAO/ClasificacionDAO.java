package org.example.DAO;

import java.util.List;
import org.example.entities.Clasificacion;

public interface ClasificacionDAO {
    Clasificacion findById(Integer id);
    Clasificacion findByCodigo(String codigo);
    Clasificacion create(Clasificacion c);
    Clasificacion update(Clasificacion c);
    boolean deleteById(Integer id);
    List<Clasificacion> findAll();
}
