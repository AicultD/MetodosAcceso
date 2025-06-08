package Entidades;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import Archivos.Acceso;

public class Compra implements Acceso {

    private int idCompra;
    private int dniUsuario;
    private Date fechaCompra;
    private float total;
    private String metodoPago;

    public static final int LONGITUD_METODOPAGO = 10;
    public static final int LONGITUD_REGISTRO = 4 + 4 + 8 + 4 + LONGITUD_METODOPAGO;

    public Compra() {
    }

    public Compra(int idCompra, int dniUsuario, Date fechaCompra, float total, String metodoPago) {
        this.idCompra = idCompra;
        this.dniUsuario = dniUsuario;
        this.fechaCompra = fechaCompra;
        this.total = total;
        this.metodoPago = metodoPago;
    }

    @Override
    public void escribir(RandomAccessFile archivo) throws IOException {
        archivo.writeInt(idCompra);
        archivo.writeInt(dniUsuario);
        archivo.writeLong(fechaCompra.getTime());
        archivo.writeFloat(total);
        escribirCampoFijo(archivo, metodoPago, LONGITUD_METODOPAGO);
    }

    @Override
    public void leer(RandomAccessFile archivo) throws IOException {
        idCompra = archivo.readInt();
        dniUsuario = archivo.readInt();
        fechaCompra = new Date(archivo.readLong());
        total = archivo.readFloat();
        metodoPago = leerCampo(archivo, LONGITUD_METODOPAGO);

        if (idCompra != 0) {
            System.out.println("----- COMPRA -----");
            System.out.println("ID Compra: " + idCompra);
            System.out.println("DNI Usuario: " + dniUsuario);
            System.out.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(fechaCompra));
            System.out.println("Total: " + total);
            System.out.println("Método de pago: " + metodoPago);
        }
    }

    @Override
    public void posicionar(RandomAccessFile archivo, int posicion) throws IOException {
        archivo.seek((long) posicion * LONGITUD_REGISTRO);
    }

    @Override
    public void modificar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.readInt(); // saltar ID
        archivo.skipBytes(4); // saltar dniUsuario
        archivo.skipBytes(8); // saltar fecha
        archivo.skipBytes(4); // saltar total
        escribirCampoFijo(archivo, metodoPago, LONGITUD_METODOPAGO);
    }

    @Override
    public void eliminar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.writeInt(0); // marcar como eliminado
        archivo.writeInt(0);
        archivo.writeLong(0);
        archivo.writeFloat(0);
        escribirCampoFijo(archivo, "", LONGITUD_METODOPAGO);
    }

    private void escribirCampoFijo(RandomAccessFile archivo, String valor, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(valor != null ? valor : "");
        while (sb.length() < longitud) {
            sb.append(" ");
        }
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        }
        archivo.write(sb.toString().getBytes("ISO-8859-1"));
    }

    private String leerCampo(RandomAccessFile archivo, int longitud) throws IOException {
        byte[] buffer = new byte[longitud];
        archivo.readFully(buffer);
        return new String(buffer, "ISO-8859-1").trim();
    }

    public static int buscarPorClave(int idCompra) {
    String ruta = Archivos.Archivo.RUTA_BASE + "\\Compras.dat";
    try (RandomAccessFile archivo = new RandomAccessFile(ruta, "r")) {
        int cantidad = (int) (archivo.length() / LONGITUD_REGISTRO);

        for (int i = 0; i < cantidad; i++) {
            archivo.seek((long) i * LONGITUD_REGISTRO);
            int idLeido = archivo.readInt(); // idCompra
            if (idLeido == idCompra) {
                return i; // encontrado
            }
        }
    } catch (IOException e) {
        System.out.println("Error en búsqueda de Compra: " + e.getMessage());
    }
    return -1; // no encontrado
}


    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(int dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
