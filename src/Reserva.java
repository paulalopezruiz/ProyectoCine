import java.io.Serializable;

public class Reserva implements Serializable {

    private int idReserva;
    private int idUsuario;
    private int idSesion;
    private int[] asientos;

    public Reserva() {
    }

    public Reserva(int idReserva, int idUsuario,
                   int idSesion, int[] asientos) {

        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idSesion = idSesion;
        this.asientos = asientos;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int[] getAsientos() {
        return asientos;
    }

    public void setAsientos(int[] asientos) {
        this.asientos = asientos;
    }

    public void mostrar() {

        System.out.println("ID reserva: " + idReserva);
        System.out.println("ID usuario: " + idUsuario);
        System.out.println("ID sesión: " + idSesion);

        System.out.println("Asientos:");

        for (int i = 0; i < asientos.length; i++) {
            System.out.println(asientos[i]);
        }
    }
}