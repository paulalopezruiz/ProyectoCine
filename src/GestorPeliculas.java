import java.io.*;
import java.util.ArrayList;

public class GestorPeliculas {


    // Muestra el menú de películas y llama al método elegido.
    public static void menuPeliculas() throws Exception {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("| ZONA DE PELICULAS |");
            System.out.println("1 - Añadir película");
            System.out.println("2 - Mostrar películas");
            System.out.println("3 - Buscar película");
            System.out.println("4 - Modificar película");
            System.out.println("5 - Eliminar película");
            System.out.println("0 - Volver");

            System.out.print("Elige una opción o 0 para volver: ");
            int opcion = Lectura.leerEntero();

            switch (opcion) {

                case 1:
                    altaPelicula();
                    break;

                case 2:
                    mostrarPeliculas();
                    break;

                case 3:
                    buscarPelicula();
                    break;

                case 4:
                    modificarPelicula();
                    break;

                case 5:
                    eliminarPelicula();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Error, intentalo de nuevo");
            }
        }
    }


    // Crea Peliculas.dat si todavía no existe
    public static void crearFicheroPeliculas() throws Exception {

        File fichero = new File("./ficheros/Peliculas.dat");

        if (!fichero.exists() || fichero.length() == 0) {

            FileOutputStream fos = new FileOutputStream(fichero);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.close();

            System.out.println("Fichero Peliculas.dat creado");
        }
    }


    // Pide los datos de una película y la guarda en el fichero
    public static void altaPelicula() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        System.out.print("Escribe el ID de la película: ");
        int id = Lectura.leerEntero();

        boolean existe = false;

        // Comprueba que no haya otra película con el mismo ID
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getIdPelicula() == id) {
                existe = true;
            }
        }

        if (existe) {

            System.out.println("Ya existe una película con ese ID");

        } else {

            System.out.print("Escribe el título: ");
            String titulo = Lectura.leerCadena();
            System.out.print("Escribe el género: ");
            String genero = Lectura.leerCadena();
            System.out.print("Escribe la duración en minutos: ");
            int duracion = Lectura.leerEntero();

            Pelicula pelicula = new Pelicula(id, titulo, genero, duracion);

            peliculas.add(pelicula);
            guardarPeliculas(peliculas);
            System.out.println("Película añadida");
        }
    }


    // Lee y muestra todas las películas guardadas
    public static void mostrarPeliculas() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        if (peliculas.size() == 0) {
            System.out.println("No hay películas");
        } else {

            for (int i = 0; i < peliculas.size(); i++) {
                peliculas.get(i).mostrar();
            }
        }
    }


    // Busca una película utilizando su ID
    public static void buscarPelicula() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        System.out.print("Escribe el ID de la película: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getIdPelicula() == id) {
                peliculas.get(i).mostrar();
                encontrada = true;
            }
        }
        if (!encontrada) {
            System.out.println("No existe esa película");
        }
    }


    // Busca una película y permite cambiar sus datos
    public static void modificarPelicula() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        System.out.print("Escribe el ID de la película a modificar: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < peliculas.size(); i++) {

            Pelicula pelicula = peliculas.get(i);

            if (pelicula.getIdPelicula() == id) {

                encontrada = true;

                System.out.print("Nuevo título: ");
                String titulo = Lectura.leerCadena();
                System.out.print("Nuevo género: ");
                String genero = Lectura.leerCadena();
                System.out.print("Nueva duración: ");
                int duracion = Lectura.leerEntero();

                pelicula.setTitulo(titulo);
                pelicula.setGenero(genero);
                pelicula.setDuracion(duracion);
            }
        }

        if (encontrada) {
            guardarPeliculas(peliculas);
            System.out.println("Película modificada");

        } else {
            System.out.println("No existe esa película");
        }
    }

    // Elimina una película por su ID
    public static void eliminarPelicula() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        System.out.print("Introduce el ID de la película que quieres eliminar: ");
        int id = Lectura.leerEntero();

        // No deja eliminarla si tiene sesiones relacionadas
        if (GestorSesiones.peliculaTieneSesiones(id)) {

            System.out.println("No puedes eliminar la película porque tiene sesiones");
            return;
        }

        boolean encontrada = false;

        for (int i = 0; i < peliculas.size(); i++) {

            if (peliculas.get(i).getIdPelicula() == id) {

                peliculas.remove(i);
                encontrada = true;

                break;
            }
        }

        if (encontrada) {
            guardarPeliculas(peliculas);
            System.out.println("Película eliminada");

        } else {
            System.out.println("No existe esa pelicula");
        }
    }

    // Lee las películas del fichero y las guarda en un ArrayList
    public static ArrayList<Pelicula> leerPeliculas() throws Exception {

        ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();

        FileInputStream fis = new FileInputStream("./ficheros/Peliculas.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        while (true) {
            try {

                Pelicula pelicula = (Pelicula) ois.readObject();
                peliculas.add(pelicula);

            } catch (EOFException e) {
                // Cuando llega al final del fichero termina la lectura.
                break;
            }
        }
        ois.close();
        return peliculas;
    }

    // Guarda todas las películas del ArrayList en Peliculas.dat
    public static void guardarPeliculas(
            ArrayList<Pelicula> peliculas) throws Exception {

        FileOutputStream fos = new FileOutputStream("./ficheros/Peliculas.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (int i = 0; i < peliculas.size(); i++) {
            oos.writeObject(peliculas.get(i));
        }
        oos.close();
    }


    // Comprueba si existe una película con ese ID
    public static boolean existePelicula(int id) throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getIdPelicula() == id) {
                return true;
            }
        }
        return false;
    }
}