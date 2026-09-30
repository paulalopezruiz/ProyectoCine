import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.thoughtworks.xstream.XStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GestorXML {

    public static void exportarXML() throws Exception {

        exportarPeliculasXML();
        exportarUsuariosXML();
        exportarSesionesXML();
        exportarReservasXML();

        System.out.println("Datos exportados a XML");
    }

    public static void exportarPeliculasXML() throws Exception {

        ArrayList<Pelicula> peliculas = GestorPeliculas.leerPeliculas();

        XStream xstream = new XStream();

        xstream.alias("pelicula", Pelicula.class);

        FileWriter fichero = new FileWriter("./ficheros/Peliculas.xml");

        xstream.toXML(peliculas, fichero);

        fichero.close();
    }

    public static void exportarUsuariosXML() throws Exception {

        ArrayList<Usuario> usuarios = GestorUsuarios.leerUsuarios();

        XStream xstream = new XStream();

        xstream.alias("usuario", Usuario.class);

        FileWriter fichero = new FileWriter("./ficheros/Usuarios.xml");

        xstream.toXML(usuarios, fichero);

        fichero.close();
    }

    public static void exportarSesionesXML() throws Exception {

        ArrayList<Sesion> sesiones = GestorSesiones.leerSesiones();

        XStream xstream = new XStream();

        xstream.alias("sesion", Sesion.class);

        FileWriter fichero = new FileWriter("./ficheros/Sesiones.xml");

        xstream.toXML(sesiones, fichero);

        fichero.close();
    }

    public static void exportarReservasXML() throws Exception {

        ArrayList<Reserva> reservas = GestorReservas.leerReservas();

        XStream xstream = new XStream();

        xstream.alias("reserva", Reserva.class);

        FileWriter fichero = new FileWriter("./ficheros/Reservas.xml");

        xstream.toXML(reservas, fichero);

        fichero.close();
    }

    public static void exportarDOM() throws Exception {

        exportarPeliculasDOM();
        exportarUsuariosDOM();
        exportarSesionesDOM();
        exportarReservasDOM();

        System.out.println("Datos exportados a XML con DOM");
    }

    private static void exportarPeliculasDOM() throws Exception {

        ArrayList<Pelicula> peliculas = GestorPeliculas.leerPeliculas();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.newDocument();

        Element raiz = documento.createElement("peliculas");
        documento.appendChild(raiz);

        for (int i = 0; i < peliculas.size(); i++) {

            Pelicula pelicula = peliculas.get(i);

            Element peliculaXML = documento.createElement("pelicula");
            raiz.appendChild(peliculaXML);

            Element id = documento.createElement("id");
            id.setTextContent(String.valueOf(pelicula.getIdPelicula()));
            peliculaXML.appendChild(id);

            Element titulo = documento.createElement("titulo");
            titulo.setTextContent(pelicula.getTitulo());
            peliculaXML.appendChild(titulo);

            Element genero = documento.createElement("genero");
            genero.setTextContent(pelicula.getGenero());
            peliculaXML.appendChild(genero);

            Element duracion = documento.createElement("duracion");
            duracion.setTextContent(String.valueOf(pelicula.getDuracion()));
            peliculaXML.appendChild(duracion);
        }

        guardarDocumentoDOM(documento, "./ficheros/PeliculasDOM.xml");
    }

    private static void exportarUsuariosDOM() throws Exception {

        ArrayList<Usuario> usuarios = GestorUsuarios.leerUsuarios();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.newDocument();

        Element raiz = documento.createElement("usuarios");
        documento.appendChild(raiz);

        for (int i = 0; i < usuarios.size(); i++) {

            Usuario usuario = usuarios.get(i);

            Element usuarioXML = documento.createElement("usuario");
            raiz.appendChild(usuarioXML);

            Element id = documento.createElement("id");
            id.setTextContent(String.valueOf(usuario.getIdUsuario()));
            usuarioXML.appendChild(id);

            Element nombre = documento.createElement("nombre");
            nombre.setTextContent(usuario.getNombre());
            usuarioXML.appendChild(nombre);

            Element email = documento.createElement("email");
            email.setTextContent(usuario.getEmail());
            usuarioXML.appendChild(email);
        }

        guardarDocumentoDOM(documento, "./ficheros/UsuariosDOM.xml");
    }

    private static void exportarSesionesDOM() throws Exception {

        ArrayList<Sesion> sesiones = GestorSesiones.leerSesiones();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.newDocument();

        Element raiz = documento.createElement("sesiones");
        documento.appendChild(raiz);

        for (int i = 0; i < sesiones.size(); i++) {

            Sesion sesion = sesiones.get(i);

            Element sesionXML = documento.createElement("sesion");
            raiz.appendChild(sesionXML);

            Element id = documento.createElement("id");
            id.setTextContent(String.valueOf(sesion.getIdSesion()));
            sesionXML.appendChild(id);

            Element idPelicula = documento.createElement("idPelicula");
            idPelicula.setTextContent(String.valueOf(sesion.getIdPelicula()));
            sesionXML.appendChild(idPelicula);

            Element fecha = documento.createElement("fecha");
            fecha.setTextContent(sesion.getFecha());
            sesionXML.appendChild(fecha);

            Element hora = documento.createElement("hora");
            hora.setTextContent(sesion.getHora());
            sesionXML.appendChild(hora);

            Element sala = documento.createElement("sala");
            sala.setTextContent(String.valueOf(sesion.getSala()));
            sesionXML.appendChild(sala);

            Element precio = documento.createElement("precio");
            precio.setTextContent(String.valueOf(sesion.getPrecio()));
            sesionXML.appendChild(precio);
        }

        guardarDocumentoDOM(documento, "./ficheros/SesionesDOM.xml");
    }

    private static void exportarReservasDOM() throws Exception {

        ArrayList<Reserva> reservas = GestorReservas.leerReservas();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.newDocument();

        Element raiz = documento.createElement("reservas");
        documento.appendChild(raiz);

        for (int i = 0; i < reservas.size(); i++) {

            Reserva reserva = reservas.get(i);

            Element reservaXML = documento.createElement("reserva");
            raiz.appendChild(reservaXML);

            Element id = documento.createElement("id");
            id.setTextContent(String.valueOf(reserva.getIdReserva()));
            reservaXML.appendChild(id);

            Element idUsuario = documento.createElement("idUsuario");
            idUsuario.setTextContent(String.valueOf(reserva.getIdUsuario()));
            reservaXML.appendChild(idUsuario);

            Element idSesion = documento.createElement("idSesion");
            idSesion.setTextContent(String.valueOf(reserva.getIdSesion()));
            reservaXML.appendChild(idSesion);

            Element asientosXML = documento.createElement("asientos");
            reservaXML.appendChild(asientosXML);

            int[] asientos = reserva.getAsientos();

            for (int j = 0; j < asientos.length; j++) {

                Element asiento = documento.createElement("asiento");
                asiento.setTextContent(String.valueOf(asientos[j]));

                asientosXML.appendChild(asiento);
            }
        }

        guardarDocumentoDOM(documento, "./ficheros/ReservasDOM.xml");
    }

    private static void guardarDocumentoDOM(Document documento, String ruta) throws Exception {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(documento);
        StreamResult resultado = new StreamResult(new File(ruta));

        transformer.transform(source, resultado);
    }

}
