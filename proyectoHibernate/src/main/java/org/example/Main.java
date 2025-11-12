package org.example;

import org.example.DAO.AnimalDAO;
import org.example.DAO.AnimalDAOImpl;
import org.example.entities.Animal;
import org.example.entities.EstadoAnimal;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final AnimalDAO animalDAO = new AnimalDAOImpl();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
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
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\nMENÚ REFUGIO");
        System.out.println("1. Registrar nuevo animal");
        System.out.println("2. Buscar animales por especie");
        System.out.println("3. Actualizar estado del animal");
        System.out.println("4. Listar todos los animales");
        System.out.println("5. Salir");
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
}
