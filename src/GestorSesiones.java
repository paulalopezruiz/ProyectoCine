import java.io.*;
import java.util.ArrayList;

public class GestorSesiones {

    public static void menuSesiones() throws Exception {

        boolean volver = false;
        while (!volver) {

            System.out.println();
            System.out.println("| ZONA DE SESIONES |");
            System.out.println("1 - Añadir sesión");
            System.out.println("2 - Mostrar sesiones");
            System.out.println("3 - Buscar sesión");
            System.out.println("4 - Modificar sesión");
            System.out.println("5 - Eliminar sesión");
            System.out.println("0 - Volver");

            System.out.print("Elige una opción o 0 para volver: ");
            int opcion = Lectura.leerEntero();

            switch (opcion) {

                case 1:
                    altaSesion();
                    break;

                case 2:
                    mostrarSesiones();
                    break;

                case 3:
                    buscarSesion();
                    break;

                case 4:
                    modificarSesion();
                    break;

                case 5:
                    eliminarSesion();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Error, intentalo de nuevo");
            }
        }
    }

    public static void crearFicheroSesiones() throws Exception {

        File fichero = new File("./ficheros/Sesiones.dat");

        if (!fichero.exists() || fichero.length() == 0) {

            FileOutputStream fos = new FileOutputStream(fichero);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.close();

            System.out.println("Fichero Sesiones.dat creado");
        }
    }

    public static void altaSesion() throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();

        System.out.print("Escribe el ID de la sesión: ");
        int id = Lectura.leerEntero();

        boolean existe = false;

        for (int i = 0; i < sesiones.size(); i++) {
            if (sesiones.get(i).getIdSesion() == id) {
                existe = true;
            }
        }

        if (existe) {
            System.out.println("Ya existe una sesión con ese ID");

        } else {

            System.out.print("Escribe el ID de la película: ");
            int idPelicula = Lectura.leerEntero();

            if (!GestorPeliculas.existePelicula(idPelicula)) {
                System.out.println("No existe una película con ese ID");
                return;
            }

            System.out.print("Escribe la fecha: ");
            String fecha = Lectura.leerCadena();

            System.out.print("Escribe la hora: ");
            String hora = Lectura.leerCadena();

            System.out.print("Escribe la sala: ");
            int sala = Lectura.leerEntero();

            System.out.print("Escribe el precio: ");
            double precio = Lectura.leerEntero();

            Sesion sesion = new Sesion(id, idPelicula, fecha, hora, sala, precio);

            sesiones.add(sesion);
            guardarSesiones(sesiones);

            System.out.println("Sesión añadida");
        }
    }

    public static void mostrarSesiones() throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();
        if (sesiones.size() == 0) {
            System.out.println("No hay sesiones");

        } else {

            for (int i = 0; i < sesiones.size(); i++) {
                sesiones.get(i).mostrar();
            }
        }
    }

    public static void buscarSesion() throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();
        System.out.print("Escribe el ID de la sesión: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < sesiones.size(); i++) {

            if (sesiones.get(i).getIdSesion() == id) {
                sesiones.get(i).mostrar();
                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println("No existe esa sesión");
        }
    }

    public static void modificarSesion() throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();

        System.out.print("Escribe el ID de la sesión a modificar: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < sesiones.size(); i++) {
            Sesion sesion = sesiones.get(i);

            if (sesion.getIdSesion() == id) {
                encontrada = true;

                System.out.print("Nuevo ID de película: ");
                int idPelicula = Lectura.leerEntero();

                if (!GestorPeliculas.existePelicula(idPelicula)) {
                    System.out.println("No existe una película con ese ID");
                    return;
                }

                System.out.print("Nueva fecha: ");
                String fecha = Lectura.leerCadena();

                System.out.print("Nueva hora: ");
                String hora = Lectura.leerCadena();

                System.out.print("Nueva sala: ");
                int sala = Lectura.leerEntero();

                System.out.print("Nuevo precio: ");
                double precio = Lectura.leerEntero();

                sesion.setIdPelicula(idPelicula);
                sesion.setFecha(fecha);
                sesion.setHora(hora);
                sesion.setSala(sala);
                sesion.setPrecio(precio);
            }
        }

        if (encontrada) {

            guardarSesiones(sesiones);
            System.out.println("Sesión modificada");

        } else {
            System.out.println("No existe esa sesión");
        }
    }

    public static void eliminarSesion() throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();

        System.out.print("Escribe el ID de la sesión que quieres eliminar: ");
        int id = Lectura.leerEntero();

        if (GestorReservas.sesionTieneReservas(id)) {
            System.out.println("No puedes eliminar la sesión porque tiene reservas");
            return;
        }

        boolean encontrada = false;

        for (int i = 0; i < sesiones.size(); i++) {

            if (sesiones.get(i).getIdSesion() == id) {
                sesiones.remove(i);
                encontrada = true;

                break;
            }
        }

        if (encontrada) {
            guardarSesiones(sesiones);
            System.out.println("Sesión eliminada");

        } else {
            System.out.println("No existe esa sesión");
        }
    }

    public static ArrayList<Sesion> leerSesiones() throws Exception {

        ArrayList<Sesion> sesiones = new ArrayList<Sesion>();

        FileInputStream fis = new FileInputStream("./ficheros/Sesiones.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        while (true) {

            try {

                Sesion sesion = (Sesion) ois.readObject();
                sesiones.add(sesion);

            } catch (EOFException e) {

                break;
            }
        }

        ois.close();
        return sesiones;
    }

    public static void guardarSesiones(
            ArrayList<Sesion> sesiones) throws Exception {

        FileOutputStream fos = new FileOutputStream("./ficheros/Sesiones.dat");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (int i = 0; i < sesiones.size(); i++) {
            oos.writeObject(sesiones.get(i));
        }

        oos.close();
    }

    public static boolean existeSesion(int id) throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();

        for (int i = 0; i < sesiones.size(); i++) {
            if (sesiones.get(i).getIdSesion() == id) {

                return true;
            }
        }
        return false;
    }

    public static boolean peliculaTieneSesiones(int idPelicula) throws Exception {

        ArrayList<Sesion> sesiones = leerSesiones();

        for (int i = 0; i < sesiones.size(); i++) {

            if (sesiones.get(i).getIdPelicula() == idPelicula) {
                return true;
            }
        }
        return false;
    }

}
