import java.io.*;
import java.util.ArrayList;

public class GestionCine {

    public static void main(String[] args) throws Exception {

        crearFicheroPeliculas();
        crearFicheroUsuarios();
        crearFicheroSesiones();
        crearFicheroReservas();

        boolean salir = false;

        while (!salir) {

            System.out.println();
            System.out.println("| ¿A que apartado quieres acceder primero? |");
            System.out.println("1 - Zona de películas");
            System.out.println("2 - Zona de usuarios");
            System.out.println("3 - Zona de sesiones");
            System.out.println("4 - Zona de reservas");
            System.out.println("0 - Salir");

            System.out.print("Elige una opción o 0 para salir: ");
            int opcion = Lectura.leerEntero();

            switch (opcion) {

                case 1:
                    menuPeliculas();
                    break;

                case 2:
                    menuUsuarios();
                    break;

                case 3:
                    menuSesiones();
                    break;

                case 4:
                    menuReservas();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Error, intentalo de nuevo");
            }
        }
    }


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


    public static void crearFicheroPeliculas() throws Exception {

        File fichero = new File("./ficheros/Peliculas.dat");

        if (!fichero.exists() || fichero.length() == 0) {

            FileOutputStream fos = new FileOutputStream(fichero);

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(null);

            oos.close();

            System.out.println("Fichero Peliculas.dat creado");
        }
    }


    public static void altaPelicula() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        System.out.print("Escribe el ID de la película: ");
        int id = Lectura.leerEntero();

        boolean existe = false;

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


    public static void eliminarPelicula() throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        System.out.print("Introduce el ID de la película que quieres eliminar: ");
        int id = Lectura.leerEntero();

        if (peliculaTieneSesiones(id)) {
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


    public static ArrayList<Pelicula> leerPeliculas() throws Exception {

        ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();

        FileInputStream fis = new FileInputStream("./ficheros/Peliculas.dat");

        ObjectInputStream ois = new ObjectInputStream(fis);

        Pelicula pelicula = (Pelicula) ois.readObject();

        while (pelicula != null) {
            peliculas.add(pelicula);
            pelicula = (Pelicula) ois.readObject();
        }

        ois.close();
        return peliculas;
    }


    public static void guardarPeliculas(
            ArrayList<Pelicula> peliculas) throws Exception {

        FileOutputStream fos = new FileOutputStream("./ficheros/Peliculas.dat");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (int i = 0; i < peliculas.size(); i++) {
            oos.writeObject(peliculas.get(i));
        }

        oos.writeObject(null);
        oos.close();
    }


    public static boolean existePelicula(int id) throws Exception {

        ArrayList<Pelicula> peliculas = leerPeliculas();

        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getIdPelicula() == id) {

                return true;
            }
        }

        return false;
    }


    public static void menuUsuarios() throws Exception {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("| ZONA DE USUARIOS |");
            System.out.println("1 - Añadir usuario");
            System.out.println("2 - Mostrar usuarios");
            System.out.println("3 - Buscar usuario");
            System.out.println("4 - Modificar usuario");
            System.out.println("5 - Eliminar usuario");
            System.out.println("0 - Volver");

            System.out.print("Elige una opción o 0 para volver: ");
            int opcion = Lectura.leerEntero();

            switch (opcion) {

                case 1:
                    altaUsuario();
                    break;

                case 2:
                    mostrarUsuarios();
                    break;

                case 3:
                    buscarUsuario();
                    break;

                case 4:
                    modificarUsuario();
                    break;

                case 5:
                    eliminarUsuario();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Error, intentalo de nuevo");
            }
        }
    }


    public static void crearFicheroUsuarios() throws Exception {

        File fichero = new File("./ficheros/Usuarios.dat");

        if (!fichero.exists() || fichero.length() == 0) {

            FileOutputStream fos = new FileOutputStream(fichero);

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(null);
            oos.close();

            System.out.println("Fichero Usuarios.dat creado");
        }
    }


    public static void altaUsuario() throws Exception {

        ArrayList<Usuario> usuarios = leerUsuarios();

        System.out.print("Escribe el ID del usuario: ");
        int id = Lectura.leerEntero();

        boolean existe = false;

        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getIdUsuario() == id) {
                existe = true;
            }
        }

        if (existe) {
            System.out.println("Ya existe un usuario con ese ID");
        } else {

            System.out.print("Escribe el nombre: ");
            String nombre = Lectura.leerCadena();

            System.out.print("Escribe el email: ");
            String email = Lectura.leerCadena();

            Usuario usuario = new Usuario(id, nombre, email);
            usuarios.add(usuario);

            guardarUsuarios(usuarios);

            System.out.println("Usuario añadido");
        }
    }


    public static void mostrarUsuarios() throws Exception {

        ArrayList<Usuario> usuarios = leerUsuarios();

        if (usuarios.size() == 0) {
            System.out.println("No hay usuarios");

        } else {

            for (int i = 0; i < usuarios.size(); i++) {
                usuarios.get(i).mostrar();
            }
        }
    }


    public static void buscarUsuario() throws Exception {

        ArrayList<Usuario> usuarios = leerUsuarios();

        System.out.print("Escribe el ID del usuario: ");
        int id = Lectura.leerEntero();

        boolean encontrado = false;

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == id) {
                usuarios.get(i).mostrar();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No existe ese usuario");
        }
    }


    public static void modificarUsuario() throws Exception {

        ArrayList<Usuario> usuarios = leerUsuarios();

        System.out.print("Escribe el ID del usuario a modificar: ");
        int id = Lectura.leerEntero();

        boolean encontrado = false;

        for (int i = 0; i < usuarios.size(); i++) {

            Usuario usuario = usuarios.get(i);
            if (usuario.getIdUsuario() == id) {

                encontrado = true;

                System.out.print("Nuevo nombre: ");
                String nombre = Lectura.leerCadena();
                System.out.print("Nuevo email: ");
                String email = Lectura.leerCadena();

                usuario.setNombre(nombre);
                usuario.setEmail(email);
            }
        }

        if (encontrado) {

            guardarUsuarios(usuarios);
            System.out.println("Usuario modificado");

        } else {
            System.out.println("No existe ese usuario");
        }
    }


    public static void eliminarUsuario() throws Exception {

        ArrayList<Usuario> usuarios = leerUsuarios();
        System.out.print("Escribe el ID del usuario que quieres eliminar: ");
        int id = Lectura.leerEntero();

        if (usuarioTieneReservas(id)) {

            System.out.println("No puedes eliminar el usuario porque tiene reservas");
            return;
        }

        boolean encontrado = false;

        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getIdUsuario() == id) {
                usuarios.remove(i);
                encontrado = true;

                break;
            }
        }

        if (encontrado) {
            guardarUsuarios(usuarios);
            System.out.println("Usuario eliminado");

        } else {
            System.out.println("No existe ese usuario");
        }
    }


    public static ArrayList<Usuario> leerUsuarios() throws Exception {

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        FileInputStream fis = new FileInputStream("./ficheros/Usuarios.dat");

        ObjectInputStream ois = new ObjectInputStream(fis);

        Usuario usuario = (Usuario) ois.readObject();

        while (usuario != null) {
            usuarios.add(usuario);
            usuario = (Usuario) ois.readObject();
        }

        ois.close();
        return usuarios;
    }


    public static void guardarUsuarios(
            ArrayList<Usuario> usuarios) throws Exception {

        FileOutputStream fos = new FileOutputStream("./ficheros/Usuarios.dat");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (int i = 0; i < usuarios.size(); i++) {
            oos.writeObject(usuarios.get(i));
        }

        oos.writeObject(null);
        oos.close();
    }


    public static boolean existeUsuario(int id) throws Exception {

        ArrayList<Usuario> usuarios = leerUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == id) {

                return true;
            }
        }

        return false;
    }


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
            oos.writeObject(null);

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

            if (!existePelicula(idPelicula)) {
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

                if (!existePelicula(idPelicula)) {
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

        if (sesionTieneReservas(id)) {
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

        Sesion sesion = (Sesion) ois.readObject();

        while (sesion != null) {
            sesiones.add(sesion);
            sesion = (Sesion) ois.readObject();
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

        oos.writeObject(null);
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