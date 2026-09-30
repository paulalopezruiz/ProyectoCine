public class GestionCine {

    public static void main(String[] args) throws Exception {

        GestorPeliculas.crearFicheroPeliculas();
        GestorUsuarios.crearFicheroUsuarios();
        GestorSesiones.crearFicheroSesiones();
        GestorReservas.crearFicheroReservas();

        boolean salir = false;

        while (!salir) {

            System.out.println("Bienvenid@");
            System.out.println("| ¿A que apartado quieres acceder primero? |");
            System.out.println("1 - Zona de películas");
            System.out.println("2 - Zona de usuarios");
            System.out.println("3 - Zona de sesiones");
            System.out.println("4 - Zona de reservas");
            System.out.println("5 - Exportar datos a XML con XStream");
            System.out.println("6 - Exportar datos a XML con DOM");
            System.out.println("7 - Exportar datos a JSON");
            System.out.println("8 - Leer datos desde JSON");
            System.out.println("0 - Salir");

            System.out.print("Elige una opción o 0 para salir: ");
            int opcion = Lectura.leerEntero();

            switch (opcion) {

                case 1:
                    GestorPeliculas.menuPeliculas();
                    break;

                case 2:
                    GestorUsuarios.menuUsuarios();
                    break;

                case 3:
                    GestorSesiones.menuSesiones();
                    break;

                case 4:
                    GestorReservas.menuReservas();
                    break;

                case 5:
                    GestorXML.exportarXML();
                    break;

                case 6:
                    GestorXML.exportarDOM();
                    break;

                case 7:
                    GestorJSON.exportarJSON();
                    break;

                case 8:
                    GestorJSON.leerJSON();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Error, intentalo de nuevo");
            }
        }
    }

}