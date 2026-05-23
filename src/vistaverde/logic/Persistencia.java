package vistaverde.logic;

import vistaverde.model.Condominio;
import java.io.*;

/**
 * Guarda y carga el estado del condominio usando serialización binaria.
 * PUNTO EXTRA +5: persistencia de datos entre sesiones.
 * No requiere librerías externas — usa java.io estándar.
 */
public class Persistencia {

    private static final String ARCHIVO = "vistaverde_datos.dat";

    /**
     * Guarda el condominio en disco.
     * @return true si se guardó correctamente.
     */
    public static boolean guardar(Condominio condominio) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(ARCHIVO)))) {
            oos.writeObject(condominio);
            return true;
        } catch (IOException e) {
            System.err.println("[Persistencia] Error al guardar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga el condominio desde disco.
     * Si no existe el archivo, retorna un Condominio nuevo.
     */
    public static Condominio cargar() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return new Condominio();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(ARCHIVO)))) {
            return (Condominio) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Persistencia] Error al cargar: " + e.getMessage());
            return new Condominio();
        }
    }

    /** Indica si ya existen datos guardados. */
    public static boolean existenDatos() {
        return new File(ARCHIVO).exists();
    }
}
