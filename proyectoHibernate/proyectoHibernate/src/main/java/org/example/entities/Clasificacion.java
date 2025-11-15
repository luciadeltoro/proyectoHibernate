package org.example.entities;

import jakarta.persistence.*;
        import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clasificaciones")
public class Clasificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // código identificador corto (ej: MAM, CAR)
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "clasificaciones", fetch = FetchType.LAZY)
    private Set<Animal> animales = new HashSet<>();

    public Clasificacion() {}

    public Clasificacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<Animal> getAnimales() { return animales; }
    public void setAnimales(Set<Animal> animales) { this.animales = animales; }

    @Override
    public String toString() {
        return "Clasificacion{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
