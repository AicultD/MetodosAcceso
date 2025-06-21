package Archivos;

import java.io.File;
import java.io.IOException;

public class Archivo {
    public static final String RUTA_BASE = "L:\\MetodosAcceso\\Sistema\\Archivos";
    
        private File archivo;
        private String nombre;
    
        public Archivo(String nombre) {
            this.nombre = nombre;
            this.archivo = new File(RUTA_BASE + File.separator + nombre);
        }
    
        public void crearArchivo() {
            try {
                if (!archivo.exists()) {
                    if (archivo.createNewFile()) {
                        System.out.println("Archivo creado: " + archivo.getAbsolutePath());
                    } else {
                        System.out.println("No se pudo crear el archivo.");
                    }
                } else {
                    System.out.println("El archivo ya existe: " + archivo.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        }
    
        // Método estático que crea todos los archivos necesarios del sistema
        public static void crearArchivosIniciales() {
            String[] nombres = {
                "Usuarios.dat",
                "Compras.dat",
                "Videojuegos.dat",
                "DetalleCompra.dat"
            };
    
            for (String nombre : nombres) {
                Archivo archivo = new Archivo(nombre);
                archivo.crearArchivo();
            }
        }
}
