package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n1. Ejercicio 01 – Shutdown");
            System.out.println("2. Ejercicio 02 – Directorio de trabajo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> Shutdown.ejecutar();
                case 2 -> DirectorioTrabajo.ejecutar();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }

        } while (opcion != 0);
    }
}
