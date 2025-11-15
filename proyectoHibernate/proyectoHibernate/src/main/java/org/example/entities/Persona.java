package org.example.entities;

import jakarta.persistence.*;
        import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    // OneToMany: una persona puede tener muchos animales
    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Animal> animales = new HashSet<>();

    public Persona() {}

    public Persona(String dni, String nombre, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
    }

    // getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Animal> getAnimales() { return animales; }
    public void setAnimales(Set<Animal> animales) { this.animales = animales; }

    // helpers para mantener la relación bidireccional
    public void addAnimal(Animal a) {
        animales.add(a);
        a.setDueno(this);
    }
    public void removeAnimal(Animal a) {
        animales.remove(a);
        a.setDueno(null);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", animalesCount=" + (animales != null ? animales.size() : 0) +
                '}';
    }
}
