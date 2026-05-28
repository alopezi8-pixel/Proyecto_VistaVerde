package proyecto_vistaverde;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistencia {

    private static final String ARCHIVO = "vistaverde_datos.dat";

    public static boolean guardar(Condominio c) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO))) {
            oos.writeObject(c);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    public static Condominio cargar() {
        File f = new File(ARCHIVO);
        if (!f.exists()) return new Condominio();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO))) {
            return (Condominio) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error al cargar: " + e.getMessage());
            return new Condominio();
        }
    }
}
