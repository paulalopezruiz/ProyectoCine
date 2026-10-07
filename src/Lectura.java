import java.util.Scanner;

public class Lectura {

    private static Scanner in = new Scanner(System.in);

    public static String leerCadena() {

        String cadena = in.nextLine();
        return cadena;
    }


    public static int leerEntero() {

        int entero = 0;
        boolean correcto = false;

        while (!correcto) {
            String entrada = in.nextLine();

            try {
                entero = Integer.parseInt(entrada);
                correcto = true;

            } catch (NumberFormatException e) {
                System.out.print("Error, tienes que escribir un numero entero: ");
            }
        }

        return entero;
    }


    public static char leerCaracter() {

        String entrada = in.nextLine();
        char caracter = entrada.charAt(0);

        return caracter;
    }
}