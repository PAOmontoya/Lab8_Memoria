package lab8p2_hectorflores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author elean
 */
public class FileManager {

    public static void renameFileOrFolder(String oldPath, String newName) {
        File oldFile = new File(oldPath);

        if (oldFile.exists()) {
            File newFile = new File(oldFile.getParent(), newName);

            if (oldFile.renameTo(newFile)) {
                System.out.println("Renombrado exitosamente.");
            } else {
                System.out.println("Error al intentar renombrar.");
            }
        } else {
            System.out.println("El archivo o carpeta no existe.");
        }
    }

    public static void createFile(String filePath) {
        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                System.out.println("Archivo creado exitosamente.");
            } else {
                System.out.println("Error al intentar crear el archivo. ¡Ya existe!");
            }
        } catch (IOException e) {
            System.out.println("Error al intentar crear el archivo.");
            e.printStackTrace();
        }
    }

    public static void copyFileOrFolder(String sourcePath, String destinationPath) {
        File source = new File(sourcePath);
        File destination = new File(destinationPath, source.getName());

        try {
            if (source.isDirectory()) {
                copyFolder(source.toPath(), destination.toPath());
            } else {
                copyFile(source.toPath(), destination.toPath());
            }

            System.out.println("Copia exitosa.");
        } catch (IOException e) {
            System.out.println("Error al intentar copiar.");
            e.printStackTrace();
        }
    }

    public static void copyFile(Path source, Path destination) throws IOException {
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void copyFolder(Path source, Path destination) throws IOException {
        Files.walk(source)
                .forEach(sourcePath -> {
                    try {
                        Files.copy(sourcePath, destination.resolve(source.relativize(sourcePath)),
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
