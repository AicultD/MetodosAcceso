package Archivos;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface Acceso {
        void escribir(RandomAccessFile archivo) throws IOException;
        void leer(RandomAccessFile archivo) throws IOException;
        void posicionar(RandomAccessFile archivo, int posicion) throws IOException;
        void modificar(RandomAccessFile archivo, int posicion) throws IOException;
        void eliminar(RandomAccessFile archivo, int posicion) throws IOException;
}
