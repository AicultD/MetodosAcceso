package Archivos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class COCArchivo {
    private File archivo;
        private String nombre;
    
        public COCArchivo(String nombre) {
            this.nombre = nombre;
            this.archivo = new File(Archivo.RUTA_BASE + File.separator + nombre);
        }
    
        public void crearYEscribir() {
            try {
                if (!archivo.exists()) {
                    archivo.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
                raf.writeUTF("Registro de prueba OUTPUT");
                raf.close();
                System.out.println("Archivo creado y escrito correctamente.");
            } catch (IOException e) {
                System.out.println("Error en escritura: " + e.getMessage());
            }
        }
    
        public void abrirYLeer() {
            try {
                if (!archivo.exists()) {
                    System.out.println("El archivo no existe.");
                    return;
                }
                RandomAccessFile raf = new RandomAccessFile(archivo, "r");
                String contenido = raf.readUTF();
                raf.close();
                System.out.println("Contenido leído: " + contenido);
            } catch (IOException e) {
                System.out.println("Error en lectura: " + e.getMessage());
            }
        }
}
