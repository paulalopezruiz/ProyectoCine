import java.io.*;
import java.util.ArrayList;

public class GestorUsuarios {

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

        if (GestorReservas.usuarioTieneReservas(id)) {

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

        while (true) {

            try {

                Usuario usuario = (Usuario) ois.readObject();
                usuarios.add(usuario);

            } catch (EOFException e) {

                break;
            }
        }

        ois.close();
        return usuarios;
    }

    public static void guardarUsuarios(ArrayList<Usuario> usuarios) throws Exception {

        FileOutputStream fos = new FileOutputStream("./ficheros/Usuarios.dat");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (int i = 0; i < usuarios.size(); i++) {
            oos.writeObject(usuarios.get(i));
        }

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

}
