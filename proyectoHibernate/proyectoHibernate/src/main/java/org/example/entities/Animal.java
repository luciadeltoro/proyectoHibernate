package org.example.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animales")
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String especie;
    private Integer edad;

    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // RELACIÓN MANY-TO-ONE con Persona (dueño)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id") // columna FK en animales
    private Persona dueno;

    // RELACIÓN MANY-TO-MANY con Clasificacion
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "animal_clasificacion",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "clasificacion_id")
    )
    private Set<Clasificacion> clasificaciones = new HashSet<>();

    public Animal() {}

    public Animal(String nombre, String especie, Integer edad, EstadoAnimal estado, String descripcion) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public Persona getDueno() { return dueno; }
    public void setDueno(Persona dueno) { this.dueno = dueno; }

    public Set<Clasificacion> getClasificaciones() { return clasificaciones; }
    public void setClasificaciones(Set<Clasificacion> clasificaciones) { this.clasificaciones = clasificaciones; }

    public void addClasificacion(Clasificacion c) {
        clasificaciones.add(c);
        c.getAnimales().add(this);
    }
    public void removeClasificacion(Clasificacion c) {
        clasificaciones.remove(c);
        c.getAnimales().remove(this);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public EstadoAnimal getEstado() { return estado; }
    public void setEstado(EstadoAnimal estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nID: ").append(id)
                .append("\nNombre: ").append(nombre)
                .append("\nEspecie: ").append(especie)
                .append("\nEdad: ").append(edad).append(" años")
                .append("\nEstado: ").append(estado)
                .append("\nDescripción: ").append(descripcion);

        if (dueno != null) {
            sb.append("\nDueño: ").append(dueno.getNombre()).append(" (").append(dueno.getDni()).append(")");
        }

        if (!clasificaciones.isEmpty()) {
            sb.append("\nClasificaciones: ");
            clasificaciones.forEach(c -> sb.append(c.getNombre()).append(" "));
        }
        sb.append("\n------------------------------");
        return sb.toString();
    }


}
