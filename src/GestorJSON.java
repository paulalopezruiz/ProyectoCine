import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GestorJSON {

    // Exporta todos los datos a ficheros JSON.
    public static void exportarJSON() throws Exception {
        exportarPeliculasJSON();
        exportarUsuariosJSON();
        exportarSesionesJSON();
        exportarReservasJSON();

        System.out.println("Datos exportados a JSON");
    }

    // Exporta las películas a Peliculas.json.
    public static void exportarPeliculasJSON() throws Exception {
        ArrayList<Pelicula> peliculas = GestorPeliculas.leerPeliculas();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter fichero = new FileWriter("./ficheros/Peliculas.json");

        gson.toJson(peliculas, fichero);
        fichero.close();
    }

    // Exporta los usuarios a Usuarios.json.
    public static void exportarUsuariosJSON() throws Exception {
        ArrayList<Usuario> usuarios = GestorUsuarios.leerUsuarios();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter fichero = new FileWriter("./ficheros/Usuarios.json");

        gson.toJson(usuarios, fichero);
        fichero.close();
    }

    // Exporta las sesiones a Sesiones.json.
    public static void exportarSesionesJSON() throws Exception {
        ArrayList<Sesion> sesiones = GestorSesiones.leerSesiones();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter fichero = new FileWriter("./ficheros/Sesiones.json");

        gson.toJson(sesiones, fichero);
        fichero.close();
    }

    // Exporta las reservas a Reservas.json.
    public static void exportarReservasJSON() throws Exception {
        ArrayList<Reserva> reservas = GestorReservas.leerReservas();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter fichero = new FileWriter("./ficheros/Reservas.json");

        gson.toJson(reservas, fichero);

        fichero.close();
    }

    // Lee y muestra todos los ficheros JSON.
    public static void leerJSON() throws Exception {
        System.out.println();
        System.out.println("| DATOS LEÍDOS DESDE JSON |");

        leerPeliculasJSON();
        leerUsuariosJSON();
        leerSesionesJSON();
        leerReservasJSON();
    }

    // Lee Peliculas.json y lo convierte en una lista de películas.
    public static void leerPeliculasJSON() throws Exception {
        Gson gson = new Gson();

        FileReader fichero = new FileReader("./ficheros/Peliculas.json");

        Type tipoLista = new TypeToken<ArrayList<Pelicula>>() {}.getType();

        ArrayList<Pelicula> peliculas = gson.fromJson(fichero, tipoLista);

        fichero.close();

        System.out.println();
        System.out.println("| PELÍCULAS JSON |");

        if (peliculas == null || peliculas.size() == 0) {
            System.out.println("No hay películas");
        } else {
            for (int i = 0; i < peliculas.size(); i++) {
                peliculas.get(i).mostrar();
            }
        }
    }

    // Lee Usuarios.json y lo convierte en una lista de usuarios.
    public static void leerUsuariosJSON() throws Exception {
        Gson gson = new Gson();

        FileReader fichero = new FileReader("./ficheros/Usuarios.json");

        Type tipoLista = new TypeToken<ArrayList<Usuario>>() {}.getType();
        ArrayList<Usuario> usuarios = gson.fromJson(fichero, tipoLista);

        fichero.close();

        System.out.println();
        System.out.println("| USUARIOS JSON |");

        if (usuarios == null || usuarios.size() == 0) {
            System.out.println("No hay usuarios");
        } else {
            for (int i = 0; i < usuarios.size(); i++) {
                usuarios.get(i).mostrar();
            }
        }
    }

    // Lee Sesiones.json y lo convierte en una lista de sesiones.
    public static void leerSesionesJSON() throws Exception {
        Gson gson = new Gson();

        FileReader fichero = new FileReader("./ficheros/Sesiones.json");

        Type tipoLista = new TypeToken<ArrayList<Sesion>>() {}.getType();

        ArrayList<Sesion> sesiones = gson.fromJson(fichero, tipoLista);

        fichero.close();

        System.out.println();
        System.out.println("| SESIONES JSON |");

        if (sesiones == null || sesiones.size() == 0) {
            System.out.println("No hay sesiones");
        } else {
            for (int i = 0; i < sesiones.size(); i++) {
                sesiones.get(i).mostrar();
            }
        }
    }

    // Lee Reservas.json y lo convierte en una lista de reservas.
    public static void leerReservasJSON() throws Exception {
        Gson gson = new Gson();

        FileReader fichero = new FileReader("./ficheros/Reservas.json");

        Type tipoLista = new TypeToken<ArrayList<Reserva>>() {}.getType();

        ArrayList<Reserva> reservas = gson.fromJson(fichero, tipoLista);

        fichero.close();

        System.out.println();
        System.out.println("| RESERVAS JSON |");

        if (reservas == null || reservas.size() == 0) {
            System.out.println("No hay reservas");
        } else {
            for (int i = 0; i < reservas.size(); i++) {
                reservas.get(i).mostrar();
            }
        }
    }
}