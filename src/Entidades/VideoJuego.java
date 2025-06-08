package Entidades;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import Archivos.Acceso;

public class VideoJuego implements Acceso {

    private int idJuego;
    private String titulo;
    private String categoria;
    private float precio;
    private int stock;
    private String descripcion;
    private Date fechaLanzamiento;

    public static final int LONG_TITULO = 30;
    public static final int LONG_CATEGORIA = 20;
    public static final int LONG_DESCRIPCION = 60;
    public static final int LONGITUD_REGISTRO = 4 + LONG_TITULO + LONG_CATEGORIA + 4 + 4 + LONG_DESCRIPCION + 8;

    public VideoJuego() {
    }

    public VideoJuego(int idJuego, String titulo, String categoria, float precio, int stock, String descripcion, Date fechaLanzamiento) {
        this.idJuego = idJuego;
        this.titulo = titulo;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public void escribir(RandomAccessFile archivo) throws IOException {
        archivo.writeInt(idJuego);
        escribirCampoFijo(archivo, titulo, LONG_TITULO);
        escribirCampoFijo(archivo, categoria, LONG_CATEGORIA);
        archivo.writeFloat(precio);
        archivo.writeInt(stock);
        escribirCampoFijo(archivo, descripcion, LONG_DESCRIPCION);
        archivo.writeLong(fechaLanzamiento.getTime());
    }

    @Override
    public void leer(RandomAccessFile archivo) throws IOException {
        idJuego = archivo.readInt();
        titulo = leerCampo(archivo, LONG_TITULO);
        categoria = leerCampo(archivo, LONG_CATEGORIA);
        precio = archivo.readFloat();
        stock = archivo.readInt();
        descripcion = leerCampo(archivo, LONG_DESCRIPCION);
        fechaLanzamiento = new Date(archivo.readLong());

        if (idJuego != 0) {
            System.out.println("----- VIDEOJUEGO -----");
            System.out.println("ID: " + idJuego);
            System.out.println("Título: " + titulo);
            System.out.println("Categoría: " + categoria);
            System.out.println("Precio: " + precio);
            System.out.println("Stock: " + stock);
            System.out.println("Descripción: " + descripcion);
            System.out.println("Fecha de lanzamiento: " + new SimpleDateFormat("dd/MM/yyyy").format(fechaLanzamiento));
        }
    }

    @Override
    public void posicionar(RandomAccessFile archivo, int posicion) throws IOException {
        archivo.seek((long) posicion * LONGITUD_REGISTRO);
    }

    @Override
    public void modificar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.readInt(); // saltar id
        escribirCampoFijo(archivo, titulo, LONG_TITULO);
        escribirCampoFijo(archivo, categoria, LONG_CATEGORIA);
        archivo.writeFloat(precio);
        archivo.writeInt(stock);
        escribirCampoFijo(archivo, descripcion, LONG_DESCRIPCION);
        archivo.writeLong(fechaLanzamiento.getTime());
    }

    @Override
    public void eliminar(RandomAccessFile archivo, int posicion) throws IOException {
        posicionar(archivo, posicion);
        archivo.writeInt(0);
        escribirCampoFijo(archivo, "", LONG_TITULO);
        escribirCampoFijo(archivo, "", LONG_CATEGORIA);
        archivo.writeFloat(0);
        archivo.writeInt(0);
        escribirCampoFijo(archivo, "", LONG_DESCRIPCION);
        archivo.writeLong(0);
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

    public static int buscarPorClave(int idJuego) {
    String ruta = Archivos.Archivo.RUTA_BASE + "\\Videojuegos.dat";
    try (RandomAccessFile archivo = new RandomAccessFile(ruta, "r")) {
        int cantidad = (int) (archivo.length() / LONGITUD_REGISTRO);

        for (int i = 0; i < cantidad; i++) {
            archivo.seek((long) i * LONGITUD_REGISTRO);
            int idLeido = archivo.readInt(); // idJuego
            if (idLeido == idJuego) {
                return i;
            }
        }
    } catch (IOException e) {
        System.out.println("Error en búsqueda de Videojuego: " + e.getMessage());
    }
    return -1;
}


    // Getters y Setters
    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }
}
