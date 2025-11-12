package org.example.entities;

import jakarta.persistence.*;
import java.io.Serializable;

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


    public Animal() {}

    public Animal(String nombre, String especie, Integer edad, EstadoAnimal estado, String descripcion) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Getters y setters
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
        return  "\n ID: " + id +
                "\n Nombre: " + nombre +
                "\n Especie: " + especie +
                "\n Edad: " + edad + " años" +
                "\n Estado: " + estado +
                "\n Descripción: " + descripcion +
                "\n------------------------------";
    }

}
