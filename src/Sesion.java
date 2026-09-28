import java.io.Serializable;

public class Sesion implements Serializable {

    private int idSesion;
    private int idPelicula;
    private String fecha;
    private String hora;
    private int sala;
    private double precio;

    public Sesion() {
    }

    public Sesion(int idSesion, int idPelicula, String fecha,
                  String hora, int sala, double precio) {

        this.idSesion = idSesion;
        this.idPelicula = idPelicula;
        this.fecha = fecha;
        this.hora = hora;
        this.sala = sala;
        this.precio = precio;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void mostrar() {

        System.out.println();
        System.out.println("ID sesión: " + idSesion);
        System.out.println("ID película: " + idPelicula);
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
        System.out.println("Sala: " + sala);
        System.out.println("Precio: " + precio);
    }
}