package org.example;

import org.example.DAO.*;
import org.example.entities.*;
import org.example.util.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final AnimalDAO animalDAO = new AnimalDAOImpl();
    private static final PersonaDAO personaDAO = new PersonaDAOImpl();
    private static final ClasificacionDAO clasificacionDAO = new ClasificacionDAOImpl();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Cerramos SessionFactory al salir
        Runtime.getRuntime().addShutdownHook(new Thread(() -> HibernateUtil.getSessionFactory().close()));

        int opcion;
        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> registrarAnimal();
                case 2 -> buscarPorEspecie();
                case 3 -> actualizarEstadoAnimal();
                case 4 -> listarTodos();
                case 5 -> asignarDueno();
                case 6 -> buscarPorDueno();
                case 7 -> agregarClasificacion();
                case 8 -> buscarPorClasificacion();
                case 9 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 9);
    }

    private static void mostrarMenu() {
        System.out.println("\nMENÚ REFUGIO");
        System.out.println("1. Registrar nuevo animal");
        System.out.println("2. Buscar animales por especie");
        System.out.println("3. Actualizar estado del animal");
        System.out.println("4. Listar todos los animales");
        System.out.println("5. Asignar dueño a un animal");
        System.out.println("6. Buscar animales por dueño");
        System.out.println("7. Agregar clasificación a un animal");
        System.out.println("8. Buscar animales por clasificación");
        System.out.println("9. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void registrarAnimal() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Especie: ");
        String especie = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        Animal animal = new Animal(nombre, especie, edad, EstadoAnimal.RECIEN_ABANDONADO, descripcion);
        animalDAO.create(animal);
        System.out.println("Animal registrado correctamente.");
    }

    private static void buscarPorEspecie() {
        System.out.print("Especie a buscar: ");
        String especie = sc.nextLine();
        List<Animal> animales = animalDAO.findByEspecie(especie);
        if (animales.isEmpty()) {
            System.out.println("No se encontraron animales de esa especie.");
        } else {
            animales.forEach(System.out::println);
        }
    }

    private static void actualizarEstadoAnimal() {
        System.out.print("ID del animal: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Estados disponibles:");
        for (EstadoAnimal estado : EstadoAnimal.values()) {
            System.out.println("- " + estado);
        }

        System.out.print("Nuevo estado: ");
        String nuevoEstadoStr = sc.nextLine().toUpperCase().replace(" ", "_");

        try {
            EstadoAnimal nuevoEstado = EstadoAnimal.valueOf(nuevoEstadoStr);
            boolean exito = ((AnimalDAOImpl) animalDAO).actualizarEstado(id, nuevoEstado);
            if (exito) System.out.println("Estado actualizado correctamente.");
            else System.out.println("Animal no encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido.");
        }
    }

    private static void listarTodos() {
        List<Animal> animales = animalDAO.findAll();
        if (animales.isEmpty()) {
            System.out.println("No hay animales registrados.");
        } else {
            animales.forEach(System.out::println);
        }
    }

    private static void asignarDueno() {
        System.out.print("ID del animal: ");
        int animalId = sc.nextInt();
        sc.nextLine();

        System.out.print("DNI del dueño: ");
        String dni = sc.nextLine();
        System.out.print("Nombre del dueño: ");
        String nombre = sc.nextLine();
        System.out.print("Email del dueño: ");
        String email = sc.nextLine();

        Persona persona = personaDAO.findByDni(dni);
        if (persona == null) {
            persona = new Persona(dni, nombre, email);
            personaDAO.create(persona);
        }

        ((AnimalDAOImpl) animalDAO).assignOwner(animalId, persona.getId());
        System.out.println("Dueño asignado correctamente.");
    }

    private static void buscarPorDueno() {
        System.out.print("ID del dueño: ");
        int ownerId = sc.nextInt();
        sc.nextLine();

        List<Animal> animales = animalDAO.findByOwnerId(ownerId);
        if (animales.isEmpty()) {
            System.out.println("No se encontraron animales para este dueño.");
        } else {
            animales.forEach(System.out::println);
        }
    }

    private static void agregarClasificacion() {
        System.out.print("ID del animal: ");
        int animalId = sc.nextInt();
        sc.nextLine();

        System.out.print("Código de clasificación: ");
        String codigo = sc.nextLine();
        System.out.print("Nombre de clasificación: ");
        String nombre = sc.nextLine();

        Clasificacion clasificacion = clasificacionDAO.findByCodigo(codigo);
        if (clasificacion == null) {
            clasificacion = new Clasificacion(codigo, nombre);
            clasificacionDAO.create(clasificacion);
        }

        ((AnimalDAOImpl) animalDAO).addClasificacionToAnimal(animalId, clasificacion.getId());
        System.out.println("Clasificación agregada correctamente.");
    }

    private static void buscarPorClasificacion() {
        System.out.print("Código de clasificación: ");
        String codigo = sc.nextLine();

        List<Animal> animales = animalDAO.findByClasificacionCodigo(codigo);
        if (animales.isEmpty()) {
            System.out.println("No se encontraron animales con esa clasificación.");
        } else {
            animales.forEach(System.out::println);
        }
    }
}
