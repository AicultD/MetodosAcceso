import Entidades.Usuario;
import Entidades.VideoJuego;
//import Ventanas.Menu;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.function.Supplier;
import Archivos.Acceso;
import Archivos.Archivo;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        iniciarMenu();
            //Archivo.crearArchivosIniciales();
    
            //llenarAleatorio("Videojuegos.dat", Principal::generarVideojuegoAleatorio, 100);
            //llenarAleatorio("Usuarios.dat", Principal::generarUsuarioAleatorio, 50);
            //mostrarRegistros("Usuarios.dat", new Entidades.Usuario(), Entidades.Usuario.LONGITUD_REGISTRO);
            //mostrarRegistros("Videojuegos.dat", new Entidades.Videojuego(), Entidades.Videojuego.LONGITUD_REGISTRO);
    }
     public static void iniciarMenu() {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //new Menu().setVisible(true);
                }
            });
        }
    
        public static void llenarAleatorio(String nombreArchivo, Supplier<? extends Acceso> generador, int cantidad) {
            try (RandomAccessFile raf = new RandomAccessFile(Archivo.RUTA_BASE + "\\" + nombreArchivo, "rw")) {
                for (int i = 0; i < cantidad; i++) {
                    Acceso entidad = generador.get();
                    raf.seek(raf.length());
                    entidad.escribir(raf);
                }
                System.out.println("Se generaron " + cantidad + " registros en " + nombreArchivo);
            } catch (Exception e) {
                System.out.println("Error al llenar archivo " + nombreArchivo + ": " + e.getMessage());
            }
        }
    
        public static void mostrarRegistros(String nombreArchivo, Acceso entidad, int longitudRegistro) {
            String rutaCompleta = Archivos.Archivo.RUTA_BASE + "\\" + nombreArchivo;
    
            try (RandomAccessFile raf = new RandomAccessFile(rutaCompleta, "r")) {
                long total = raf.length() / longitudRegistro;
    
                for (int i = 0; i < total; i++) {
                    entidad.posicionar(raf, i);
                    entidad.leer(raf); // Cada clase imprime su info
                }
    
            } catch (IOException e) {
                System.out.println("Error al leer archivo " + nombreArchivo + ": " + e.getMessage());
            }
        }
    
        public static Usuario generarUsuarioAleatorio() {
            String[] nombres = {"Juan", "Ana", "Luis", "Carla"};
            String[] apellidos = {"Gomez", "Ramos", "Lopez", "Fernandez"};
    
            Random rand = new Random();
            int dni = 10000000 + rand.nextInt(89999999);
            String nombre = nombres[rand.nextInt(nombres.length)];
            String apellido = apellidos[rand.nextInt(apellidos.length)];
            String correo = (nombre + apellido + "@mail.com").toLowerCase();
            Date fecha = new Date();
    
            Usuario u = new Usuario();
            u.setDNI(dni);
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCorreo(correo);
            u.setFechaRegistro(fecha);
            return u;
        }
    
        public static VideoJuego generarVideojuegoAleatorio() {
            String[] titulos = {"FIFA", "Minecraft", "Fortnite", "Zelda"};
            String[] categorias = {"Aventura", "Accion", "Deportes"};
            String[] descripciones = {"Genial", "Muy divertido", "Clásico"};
    
            Random rand = new Random();
            String titulo = titulos[rand.nextInt(titulos.length)] + " " + rand.nextInt(999);
            String categoria = categorias[rand.nextInt(categorias.length)];
            String descripcion = descripciones[rand.nextInt(descripciones.length)];
    
            float precio = 30 + rand.nextFloat() * 70;
            int stock = rand.nextInt(100);
            Date fecha = new GregorianCalendar(2015 + rand.nextInt(10), rand.nextInt(12), rand.nextInt(28) + 1).getTime();
    
            return new VideoJuego(rand.nextInt(9000) + 1000, titulo, categoria, precio, stock, descripcion, fecha);
        }
}
