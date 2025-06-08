package Entidades;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import Archivos.Acceso;

public class Usuario implements Acceso {

    private int DNI;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaRegistro;

    public static final int LONGITUD_NOMBRE = 30;
    public static final int LONGITUD_APELLIDO = 30;
    public static final int LONGITUD_CORREO = 40;
    public static final int LONGITUD_REGISTRO = 4 + LONGITUD_NOMBRE + LONGITUD_APELLIDO + LONGITUD_CORREO + 8;

    public Usuario() {
    }

    public Usuario(int DNI, String nombre, String apellido, String correo, Date fechaRegistro) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public void escribir(RandomAccessFile archivo) throws IOException {
        archivo.writeInt(DNI);
        escribirCampoFijo(archivo, nombre, LONGITUD_NOMBRE);
        escribirCampoFijo(archivo, apellido, LONGITUD_APELLIDO);
        escribirCampoFijo(archivo, correo, LONGITUD_CORREO);
        archivo.writeLong(fechaRegistro.getTime()); // Fecha en milisegundos
    }

    @Override
    public void leer(RandomAccessFile archivo) throws IOException {
        DNI = archivo.readInt();
        nombre = leerCampo(archivo, LONGITUD_NOMBRE);
        apellido = leerCampo(archivo, LONGITUD_APELLIDO);
        correo = leerCampo(archivo, LONGITUD_CORREO);
        fechaRegistro = new Date(archivo.readLong());

        // Mostrar
        if (DNI != 0) {
            System.out.println("----- USUARIO -----");
            System.out.println("DNI: " + DNI);
            System.out.println("Nombre: " + nombre);
            System.out.println("Apellido: " + apellido);
            System.out.println("Correo: " + correo);
            System.out.println("Fecha de registro: " + new SimpleDateFormat("dd/MM/yyyy").format(fechaRegistro));
        }
    }

    @Override
    public void posicionar(RandomAccessFile archivo, int posicion) throws IOException {
        archivo.seek((long) posicion * LONGITUD_REGISTRO);
    }

    @Override
    public void modificar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.readInt(); // saltar DNI
        escribirCampoFijo(archivo, nombre, LONGITUD_NOMBRE);
        escribirCampoFijo(archivo, apellido, LONGITUD_APELLIDO);
        escribirCampoFijo(archivo, correo, LONGITUD_CORREO);
        archivo.skipBytes(8); // saltar fecha sin modificarla
    }

    @Override
    public void eliminar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.writeInt(0); // DNI 0 indica eliminación
        escribirCampoFijo(archivo, "", LONGITUD_NOMBRE);
        escribirCampoFijo(archivo, "", LONGITUD_APELLIDO);
        escribirCampoFijo(archivo, "", LONGITUD_CORREO);
        archivo.writeLong(0); // fecha eliminada
    }

    private void escribirCampoFijo(RandomAccessFile archivo, String valor, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(valor != null ? valor : "");
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        } else {
            while (sb.length() < longitud) {
                sb.append(" ");
            }
        }
        archivo.write(sb.toString().getBytes("ISO-8859-1"));
    }

    private String leerCampo(RandomAccessFile archivo, int longitud) throws IOException {
        byte[] buffer = new byte[longitud];
        archivo.readFully(buffer);
        return new String(buffer, "ISO-8859-1").trim();
    }

    public static int buscarPorClave(int claveDNI) {
    String ruta = Archivos.Archivo.RUTA_BASE + "\\Usuarios.dat";
    try (RandomAccessFile archivo = new RandomAccessFile(ruta, "r")) {
        int cantidad = (int) (archivo.length() / LONGITUD_REGISTRO);

        for (int i = 0; i < cantidad; i++) {
            archivo.seek((long) i * LONGITUD_REGISTRO);
            int dniLeido = archivo.readInt();
            if (dniLeido == claveDNI) {
                return i; // encontrado
            }
        }
    } catch (IOException e) {
        System.out.println("Error en búsqueda: " + e.getMessage());
    }
    return -1; // no encontrado
}



    // --- Getters y Setters ---
    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        if (String.valueOf(DNI).matches("\\d{8}")) {
            this.DNI = DNI;
        } else {
            throw new IllegalArgumentException("DNI debe tener exactamente 8 dígitos.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}