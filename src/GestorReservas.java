import java.io.*;
import java.util.ArrayList;

public class GestorReservas {

    public static void menuReservas() throws Exception {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("| ZONA DE RESERVAS |");
            System.out.println("1 - Añadir reserva");
            System.out.println("2 - Mostrar reservas");
            System.out.println("3 - Buscar reserva");
            System.out.println("4 - Modificar reserva");
            System.out.println("5 - Eliminar reserva");
            System.out.println("0 - Volver");

            System.out.print("Elige una opción o 0 para volver: ");
            int opcion = Lectura.leerEntero();

            switch (opcion) {

                case 1:
                    altaReserva();
                    break;

                case 2:
                    mostrarReservas();
                    break;

                case 3:
                    buscarReserva();
                    break;

                case 4:
                    modificarReserva();
                    break;

                case 5:
                    eliminarReserva();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Error, intentalo de nuevo");
            }
        }
    }

    public static void crearFicheroReservas() throws Exception {

        File fichero = new File("./ficheros/Reservas.dat");

        if (!fichero.exists() || fichero.length() == 0) {

            FileOutputStream fos = new FileOutputStream(fichero);

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.close();
            System.out.println("Fichero Reservas.dat creado");
        }
    }

    public static void altaReserva() throws Exception {

        ArrayList<Reserva> reservas = leerReservas();
        System.out.print("Escribe el ID de la reserva: ");
        int id = Lectura.leerEntero();

        boolean existe = false;

        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdReserva() == id) {
                existe = true;
            }
        }

        if (existe) {
            System.out.println("Ya existe una reserva con ese ID");
            return;
        }

        System.out.print("Escribe el ID del usuario: ");
        int idUsuario = Lectura.leerEntero();

        if (!GestorUsuarios.existeUsuario(idUsuario)) {
            System.out.println("No existe ese usuario");
            return;
        }

        System.out.print("Escribe el ID de la sesión: ");
        int idSesion = Lectura.leerEntero();

        if (!GestorSesiones.existeSesion(idSesion)) {
            System.out.println("No existe esa sesión");
            return;
        }

        int[] asientos = pedirAsientos(reservas, idSesion, -1);

        Reserva reserva = new Reserva(id, idUsuario, idSesion, asientos);

        reservas.add(reserva);
        guardarReservas(reservas);

        System.out.println("Reserva añadida");
    }

    public static void mostrarReservas() throws Exception {

        ArrayList<Reserva> reservas = leerReservas();

        if (reservas.size() == 0) {

            System.out.println("No hay reservas");
        } else {

            for (int i = 0; i < reservas.size(); i++) {
                reservas.get(i).mostrar();
            }
        }
    }

    public static void buscarReserva() throws Exception {

        ArrayList<Reserva> reservas = leerReservas();

        System.out.print("Escribe el ID de la reserva: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < reservas.size(); i++) {

            if (reservas.get(i).getIdReserva() == id) {
                reservas.get(i).mostrar();
                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println("No existe esa reserva");
        }
    }

    public static void modificarReserva() throws Exception {

        ArrayList<Reserva> reservas = leerReservas();

        System.out.print("Escribe el ID de la reserva a modificar: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < reservas.size(); i++) {

            Reserva reserva = reservas.get(i);
            if (reserva.getIdReserva() == id) {

                encontrada = true;

                System.out.print("Nuevo ID del usuario: ");
                int idUsuario = Lectura.leerEntero();

                if (!GestorUsuarios.existeUsuario(idUsuario)) {
                    System.out.println("No existe ese usuario");
                    return;
                }

                System.out.print("Nuevo ID de sesión: ");
                int idSesion = Lectura.leerEntero();
                if (!GestorSesiones.existeSesion(idSesion)) {
                    System.out.println("No existe esa sesión");

                    return;
                }
                int[] asientos = pedirAsientos(reservas, idSesion, id);

                reserva.setIdUsuario(idUsuario);
                reserva.setIdSesion(idSesion);
                reserva.setAsientos(asientos);
            }
        }

        if (encontrada) {
            guardarReservas(reservas);

            System.out.println("Reserva modificada");
        } else {
            System.out.println("No existe esa reserva");
        }
    }

    public static void eliminarReserva() throws Exception {
        ArrayList<Reserva> reservas = leerReservas();

        System.out.print("Escribe el ID de la reserva que quieres eliminar: ");
        int id = Lectura.leerEntero();

        boolean encontrada = false;

        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdReserva() == id) {

                reservas.remove(i);
                encontrada = true;
                break;
            }
        }

        if (encontrada) {
            guardarReservas(reservas);
            System.out.println("Reserva eliminada");

        } else {
            System.out.println("No existe esa reserva");
        }
    }

    public static ArrayList<Reserva> leerReservas() throws Exception {

        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        FileInputStream fis = new FileInputStream("./ficheros/Reservas.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        while (true) {

            try {
                Reserva reserva = (Reserva) ois.readObject();
                reservas.add(reserva);

            } catch (EOFException e) {

                break;
            }
        }

        ois.close();
        return reservas;
    }

    public static void guardarReservas(ArrayList<Reserva> reservas) throws Exception {

        FileOutputStream fos = new FileOutputStream("./ficheros/Reservas.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (int i = 0; i < reservas.size(); i++) {
            oos.writeObject(reservas.get(i));
        }

        oos.close();
    }

    public static int[] pedirAsientos(ArrayList<Reserva> reservas, int idSesion, int idReservaIgnorar) throws Exception {

        System.out.print("¿Cuántos asientos quieres reservar?: ");
        int cantidad = Lectura.leerEntero();

        while (cantidad <= 0) {

            System.out.println("Cantidad no válida");

            System.out.print("¿Cuántos asientos quieres reservar?: ");
            cantidad = Lectura.leerEntero();
        }

        int[] asientos = new int[cantidad];

        for (int i = 0; i < asientos.length; i++) {

            boolean valido = false;

            while (!valido) {
                System.out.print("Escribe el asiento " + (i + 1) + " que quieras: ");
                int asiento = Lectura.leerEntero();
                boolean repetido = false;

                for (int j = 0; j < i; j++) {
                    if (asientos[j] == asiento) {
                        repetido = true;
                    }
                }

                if (asiento <= 0) {
                    System.out.println("Tiene que ser un asiento mayor a 0");
                } else if (repetido) {
                    System.out.println("Ya has elegido ese asiento");
                } else if (asientoOcupado(reservas, idSesion, asiento, idReservaIgnorar)) {
                    System.out.println("Ese asiento ya está reservado");
                } else {

                    asientos[i] = asiento;
                    valido = true;
                }
            }
        }
        return asientos;
    }

    public static boolean asientoOcupado(ArrayList<Reserva> reservas, int idSesion, int asiento, int idReservaIgnorar) {

        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);

            if (reserva.getIdSesion() == idSesion && reserva.getIdReserva() != idReservaIgnorar) {

                int[] asientos = reserva.getAsientos();

                for (int j = 0; j < asientos.length; j++) {
                    if (asientos[j] == asiento) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean usuarioTieneReservas(int idUsuario) throws Exception {

        ArrayList<Reserva> reservas = leerReservas();

        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdUsuario() == idUsuario) {
                return true;
            }
        }
        return false;
    }

    public static boolean sesionTieneReservas(int idSesion) throws Exception {
        ArrayList<Reserva> reservas = leerReservas();

        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdSesion() == idSesion) {
                return true;
            }
        }
        return false;
    }

}
