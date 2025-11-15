package org.example;
import java.io.IOException;

public class DirectorioTrabajo {

    public static void ejecutar() {
        System.out.println("\n Directorio de trabajo");

        String os = System.getProperty("os.name").toLowerCase();
        String userDir = System.getProperty("user.dir");
        String tempDir = os.contains("win") ? "C:\\temp" : "/tmp";

        ProcessBuilder pb = new ProcessBuilder();
        pb.command(os.contains("win") ? "cmd.exe" : "bash", "-c", os.contains("win") ? "dir" : "ls");

        System.out.println("\n1️⃣  Directorio actual (ProcessBuilder.directory()): " + pb.directory());
        System.out.println("1️⃣  user.dir: " + userDir);

        // Cambiar propiedad user.dir
        System.setProperty("user.dir", tempDir);
        System.out.println("\n2️⃣  user.dir modificado: " + System.getProperty("user.dir"));

        // Cambiar directorio de trabajo del ProcessBuilder
        pb.directory(new java.io.File(tempDir));
        System.out.println("3️⃣  ProcessBuilder.directory() cambiado: " + pb.directory());

        // Ejecutar comando en el nuevo directorio
        try {
            Process proceso = pb.start();
            proceso.waitFor();
            System.out.println("\n Comando ejecutado correctamente en: " + tempDir);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
