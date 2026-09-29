import java.io.Serializable;

public class Pelicula implements Serializable {

    private int idPelicula;
    private String titulo;
    private String genero;
    private int duracion;

    public Pelicula() {
    }

    public Pelicula(int idPelicula, String titulo, String genero, int duracion) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void mostrar() {
        System.out.println();
        System.out.println("ID: " + idPelicula);
        System.out.println("Título: " + titulo);
        System.out.println("Género: " + genero);
        System.out.println("Duración: " + duracion + " minutos");
    }
}