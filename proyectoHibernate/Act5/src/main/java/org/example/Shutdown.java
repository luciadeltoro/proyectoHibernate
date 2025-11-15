package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Shutdown {

    public static void ejecutar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n Control de apagado del sistema");
        System.out.print("Seleccione acción (apagar / reiniciar / suspender): ");
        String accion = sc.nextLine().trim().toLowerCase();

        System.out.print("Tiempo de espera (en segundos): ");
        int tiempo = Integer.parseInt(sc.nextLine());

        String os = System.getProperty("os.name").toLowerCase();
        String comando = prepararComando(os, accion, tiempo);

        if (comando == null) {
            System.out.println("Acción o sistema operativo no soportado.");
            return;
        }

        System.out.println("\n Sistema operativo detectado: " + os);
        System.out.println(" Comando preparado:");
        System.out.println(">> " + comando);

        System.out.print("\n¿Desea ejecutar el comando? (s/n): ");
        String respuesta = sc.nextLine().trim().toLowerCase();

        if (respuesta.equals("s")) {
            try {
                ProcessBuilder pb;

                // En macOS/Linux usamos bash; en Windows, cmd.exe
                if (os.contains("win")) {
                    pb = new ProcessBuilder("cmd.exe", "/c", comando);
                } else {
                    pb = new ProcessBuilder("bash", "-c", comando);
                }

                System.out.println("\nEjecutando...");
                Process proceso = pb.start();
                proceso.waitFor();
                System.out.println("Comando ejecutado (puede que necesites permisos de administrador).");

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Comando no ejecutado (solo mostrado en consola).");
        }
    }

    private static String prepararComando(String os, String accion, int tiempo) {
        if (os.contains("win")) {
            switch (accion) {
                case "apagar":
                    return "shutdown /s /t " + tiempo;
                case "reiniciar":
                    return "shutdown /r /t " + tiempo;
                case "suspender":
                    return "rundll32.exe powrprof.dll,SetSuspendState Sleep";
                default:
                    return null;
            }
        } else if (os.contains("linux") || os.contains("mac")) {
            int minutos = Math.max(1, tiempo / 60);
            switch (accion) {
                case "apagar":
                    return "shutdown -h +" + minutos;
                case "reiniciar":
                    return "shutdown -r +" + minutos;
                case "suspender":
                    return "systemctl suspend";
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
}
