package Entidades;

import java.io.IOException;
import java.io.RandomAccessFile;

import Archivos.Acceso;

public class DetalleCompra implements Acceso {

    private int idCompra;     // 4 bytes
    private int idJuego;      // 4 bytes
    private int cantidad;     // 4 bytes
    private float precioUnitario; // 4 bytes
    private float subtotal;       // 4 bytes

    public static final int LONGITUD_REGISTRO = 4 + 4 + 4 + 4 + 4; // Total: 20 bytes

    public DetalleCompra() {
    }

    public DetalleCompra(int idCompra, int idJuego, int cantidad, float precioUnitario, float subtotal) {
        this.idCompra = idCompra;
        this.idJuego = idJuego;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    @Override
    public void escribir(RandomAccessFile archivo) throws IOException {
        archivo.writeInt(idCompra);
        archivo.writeInt(idJuego);
        archivo.writeInt(cantidad);
        archivo.writeFloat(precioUnitario);
        archivo.writeFloat(subtotal);
    }

    @Override
    public void leer(RandomAccessFile archivo) throws IOException {
        idCompra = archivo.readInt();
        idJuego = archivo.readInt();
        cantidad = archivo.readInt();
        precioUnitario = archivo.readFloat();
        subtotal = archivo.readFloat();

        if (idCompra != 0 && idJuego != 0) {
            System.out.println("----- DETALLE COMPRA -----");
            System.out.println("ID Compra: " + idCompra);
            System.out.println("ID Juego: " + idJuego);
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Precio Unitario: " + precioUnitario);
            System.out.println("Subtotal: " + subtotal);
        }
    }

    @Override
    public void posicionar(RandomAccessFile archivo, int posicion) throws IOException {
        archivo.seek((long) posicion * LONGITUD_REGISTRO);
    }

    @Override
    public void modificar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.readInt(); // saltar idCompra
        archivo.readInt(); // saltar idJuego
        archivo.writeInt(cantidad);
        archivo.writeFloat(precioUnitario);
        archivo.writeFloat(subtotal);
    }

    @Override
    public void eliminar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.writeInt(0); // idCompra en 0 = eliminado
        archivo.writeInt(0); // idJuego en 0 = eliminado
        archivo.writeInt(0);
        archivo.writeFloat(0.0f);
        archivo.writeFloat(0.0f);
    }

    public static int buscarPorClave(int idCompra, int idJuego) {
    String ruta = Archivos.Archivo.RUTA_BASE + "\\DetalleCompra.dat";
    try (RandomAccessFile archivo = new RandomAccessFile(ruta, "r")) {
        int cantidad = (int) (archivo.length() / LONGITUD_REGISTRO);

        for (int i = 0; i < cantidad; i++) {
            archivo.seek((long) i * LONGITUD_REGISTRO);
            int idCompraLeido = archivo.readInt();
            int idJuegoLeido = archivo.readInt();
            if (idCompraLeido == idCompra && idJuegoLeido == idJuego) {
                return i;
            }
        }
    } catch (IOException e) {
        System.out.println("Error en búsqueda de DetalleCompra: " + e.getMessage());
    }
    return -1;
}


    // Getters y setters
    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
