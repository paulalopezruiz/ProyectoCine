let datos = {
    peliculas: cargarDatos("peliculas"),
    usuarios: cargarDatos("usuarios"),
    sesiones: cargarDatos("sesiones"),
    reservas: cargarDatos("reservas")
};

let editando = null;
let tipoActual = "";
let accionActual = "";
let registroAEliminar = null;

let configuracion = {
    peliculas: {
        singular: "película",
        plural: "películas",
        formulario: "formularioPelicula",
        tituloFormulario: "tituloFormularioPelicula",
        mensaje: "mensajePelicula",
        tabla: "tablaPeliculas",
        idInput: "idPelicula"
    },
    usuarios: {
        singular: "usuario",
        plural: "usuarios",
        formulario: "formularioUsuario",
        tituloFormulario: "tituloFormularioUsuario",
        mensaje: "mensajeUsuario",
        tabla: "tablaUsuarios",
        idInput: "idUsuario"
    },
    sesiones: {
        singular: "sesión",
        plural: "sesiones",
        formulario: "formularioSesion",
        tituloFormulario: "tituloFormularioSesion",
        mensaje: "mensajeSesion",
        tabla: "tablaSesiones",
        idInput: "idSesion"
    },
    reservas: {
        singular: "reserva",
        plural: "reservas",
        formulario: "formularioReserva",
        tituloFormulario: "tituloFormularioReserva",
        mensaje: "mensajeReserva",
        tabla: "tablaReservas",
        idInput: "idReserva"
    }
};

let ventanaAccion = document.getElementById("ventanaAccion");
let tituloAccion = document.getElementById("tituloAccion");
let textoAccion = document.getElementById("textoAccion");
let zonaIdAccion = document.getElementById("zonaIdAccion");
let labelIdAccion = document.getElementById("labelIdAccion");
let idAccion = document.getElementById("idAccion");
let mensajeAccion = document.getElementById("mensajeAccion");
let botonesAccion = document.getElementById("botonesAccion");
let aceptarAccion = document.getElementById("aceptarAccion");
let cancelarAccion = document.getElementById("cancelarAccion");
let confirmacionEliminar = document.getElementById("confirmacionEliminar");
let textoConfirmacion = document.getElementById("textoConfirmacion");
let confirmarEliminar = document.getElementById("confirmarEliminar");
let cancelarEliminar = document.getElementById("cancelarEliminar");

/* Botones principales */

document.querySelectorAll("[data-tipo][data-accion]").forEach(function (boton) {
    boton.addEventListener("click", function () {
        let tipo = boton.dataset.tipo;
        let accion = boton.dataset.accion;

        if (accion == "anadir") {
            abrirFormulario(tipo);
        }

        if (accion == "mostrar") {
            mostrarTabla(tipo);
            mostrarMensaje(tipo, "Mostrando todos los " + configuracion[tipo].plural);
        }

        if (accion == "buscar") {
            abrirAccion(tipo, "buscar");
        }

        if (accion == "modificar") {
            abrirAccion(tipo, "modificar");
        }

        if (accion == "eliminar") {
            abrirAccion(tipo, "eliminar");
        }
    });
});

/* Guardar formularios */

document.querySelectorAll("[data-guardar]").forEach(function (boton) {
    boton.addEventListener("click", function () {
        guardarFormulario(boton.dataset.guardar);
    });
});

/* Cancelar formularios */

document.querySelectorAll("[data-cancelar]").forEach(function (boton) {
    boton.addEventListener("click", function () {
        cerrarFormulario(boton.dataset.cancelar);
    });
});

/* Abrir formulario */

function abrirFormulario(tipo, registro = null) {
    cerrarTodosLosFormularios();

    let config = configuracion[tipo];
    let formulario = document.getElementById(config.formulario);
    let titulo = document.getElementById(config.tituloFormulario);
    let campoId = document.getElementById(config.idInput);

    limpiarFormulario(tipo);

    if (registro == null) {
        editando = null;
        titulo.textContent = "Añadir " + config.singular;
        campoId.disabled = false;
    } else {
        editando = {
            tipo: tipo,
            id: registro.id
        };

        titulo.textContent = "Modificar " + config.singular;
        rellenarFormulario(tipo, registro);
        campoId.disabled = true;
    }

    formulario.classList.remove("oculto");

    let primerCampo = formulario.querySelector("input:not(:disabled)");

    if (primerCampo != null) {
        primerCampo.focus();
    }
}

/* Leer formularios */

function leerFormulario(tipo) {
    if (tipo == "peliculas") {
        return {
            id: Number(document.getElementById("idPelicula").value),
            titulo: document.getElementById("tituloPelicula").value.trim(),
            genero: document.getElementById("generoPelicula").value.trim(),
            duracion: Number(document.getElementById("duracionPelicula").value)
        };
    }

    if (tipo == "usuarios") {
        return {
            id: Number(document.getElementById("idUsuario").value),
            nombre: document.getElementById("nombreUsuario").value.trim(),
            email: document.getElementById("emailUsuario").value.trim()
        };
    }

    if (tipo == "sesiones") {
        return {
            id: Number(document.getElementById("idSesion").value),
            idPelicula: Number(document.getElementById("idPeliculaSesion").value),
            fecha: document.getElementById("fechaSesion").value,
            hora: document.getElementById("horaSesion").value,
            sala: Number(document.getElementById("salaSesion").value),
            precio: Number(document.getElementById("precioSesion").value)
        };
    }

    if (tipo == "reservas") {
        return {
            id: Number(document.getElementById("idReserva").value),
            idUsuario: Number(document.getElementById("idUsuarioReserva").value),
            idSesion: Number(document.getElementById("idSesionReserva").value),
            asientos: leerAsientos(document.getElementById("asientosReserva").value)
        };
    }
}

/* Guardar */

function guardarFormulario(tipo) {
    let registro = leerFormulario(tipo);
    let idAnterior = null;

    if (editando != null && editando.tipo == tipo) {
        idAnterior = editando.id;
        registro.id = idAnterior;
    }

    let error = validarRegistro(tipo, registro, idAnterior);

    if (error != "") {
        mostrarMensaje(tipo, error);
        return;
    }

    if (idAnterior == null) {
        datos[tipo].push(registro);
        mostrarMensaje(tipo, capitalize(configuracion[tipo].singular) + " añadida/o correctamente");
    } else {
        let posicion = datos[tipo].findIndex(function (elemento) {
            return elemento.id == idAnterior;
        });

        if (posicion != -1) {
            datos[tipo][posicion] = registro;
        }

        mostrarMensaje(tipo, capitalize(configuracion[tipo].singular) + " modificada/o correctamente");
    }

    guardarDatos(tipo);
    mostrarTabla(tipo);
    cerrarFormulario(tipo);
}

/* Validaciones */

function validarRegistro(tipo, registro, idAnterior) {
    if (!Number.isInteger(registro.id) || registro.id <= 0) {
        return "El ID tiene que ser un número mayor que 0";
    }

    let repetido = datos[tipo].some(function (elemento) {
        return elemento.id == registro.id && elemento.id != idAnterior;
    });

    if (repetido) {
        return "Ya existe " + articulo(tipo) + " con ese ID";
    }

    if (tipo == "peliculas") {
        if (registro.titulo == "") {
            return "El título no puede estar vacío";
        }

        if (registro.genero == "") {
            return "El género no puede estar vacío";
        }

        if (registro.duracion <= 0) {
            return "La duración tiene que ser mayor que 0";
        }
    }

    if (tipo == "usuarios") {
        if (registro.nombre == "") {
            return "El nombre no puede estar vacío";
        }

        let expresionEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!expresionEmail.test(registro.email)) {
            return "Introduce un email válido";
        }
    }

    if (tipo == "sesiones") {
        let peliculaExiste = datos.peliculas.some(function (pelicula) {
            return pelicula.id == registro.idPelicula;
        });

        if (!peliculaExiste) {
            return "No existe una película con ese ID";
        }

        if (registro.fecha == "") {
            return "Selecciona una fecha";
        }

        if (registro.hora == "") {
            return "Selecciona una hora";
        }

        if (registro.sala <= 0) {
            return "La sala tiene que ser mayor que 0";
        }

        if (registro.precio <= 0) {
            return "El precio tiene que ser mayor que 0";
        }
    }

    if (tipo == "reservas") {
        let usuarioExiste = datos.usuarios.some(function (usuario) {
            return usuario.id == registro.idUsuario;
        });

        if (!usuarioExiste) {
            return "No existe un usuario con ese ID";
        }

        let sesionExiste = datos.sesiones.some(function (sesion) {
            return sesion.id == registro.idSesion;
        });

        if (!sesionExiste) {
            return "No existe una sesión con ese ID";
        }

        if (registro.asientos.length == 0) {
            return "Introduce al menos un asiento";
        }

        for (let i = 0; i < registro.asientos.length; i++) {
            if (Number.isNaN(registro.asientos[i])) {
                return "Los asientos solo pueden contener números";
            }

            if (!Number.isInteger(registro.asientos[i])) {
                return "Los asientos tienen que ser números enteros";
            }

            if (registro.asientos[i] <= 0) {
                return "Los asientos tienen que ser mayores que 0";
            }
        }

        let asientosSinRepetir = new Set(registro.asientos);

        if (asientosSinRepetir.size != registro.asientos.length) {
            return "No puedes repetir un asiento en la misma reserva";
        }

        let asientosOcupados = [];

        for (let i = 0; i < datos.reservas.length; i++) {
            let reserva = datos.reservas[i];

            if (reserva.id == idAnterior) {
                continue;
            }

            if (reserva.idSesion == registro.idSesion) {
                for (let j = 0; j < reserva.asientos.length; j++) {
                    asientosOcupados.push(Number(reserva.asientos[j]));
                }
            }
        }

        for (let i = 0; i < registro.asientos.length; i++) {
            if (asientosOcupados.includes(registro.asientos[i])) {
                return "El asiento " + registro.asientos[i] + " ya está reservado en esa sesión";
            }
        }
    }

    return "";
}

/* Rellenar formulario */

function rellenarFormulario(tipo, registro) {
    if (tipo == "peliculas") {
        document.getElementById("idPelicula").value = registro.id;
        document.getElementById("tituloPelicula").value = registro.titulo;
        document.getElementById("generoPelicula").value = registro.genero;
        document.getElementById("duracionPelicula").value = registro.duracion;
    }

    if (tipo == "usuarios") {
        document.getElementById("idUsuario").value = registro.id;
        document.getElementById("nombreUsuario").value = registro.nombre;
        document.getElementById("emailUsuario").value = registro.email;
    }

    if (tipo == "sesiones") {
        document.getElementById("idSesion").value = registro.id;
        document.getElementById("idPeliculaSesion").value = registro.idPelicula;
        document.getElementById("fechaSesion").value = registro.fecha;
        document.getElementById("horaSesion").value = registro.hora;
        document.getElementById("salaSesion").value = registro.sala;
        document.getElementById("precioSesion").value = registro.precio;
    }

    if (tipo == "reservas") {
        document.getElementById("idReserva").value = registro.id;
        document.getElementById("idUsuarioReserva").value = registro.idUsuario;
        document.getElementById("idSesionReserva").value = registro.idSesion;
        document.getElementById("asientosReserva").value = registro.asientos.join(", ");
    }
}

/* Limpiar formulario */

function limpiarFormulario(tipo) {
    let formulario = document.getElementById(configuracion[tipo].formulario);
    let campos = formulario.querySelectorAll("input");

    campos.forEach(function (campo) {
        campo.value = "";
        campo.disabled = false;
    });
}

/* Cerrar formulario */

function cerrarFormulario(tipo) {
    document.getElementById(configuracion[tipo].formulario).classList.add("oculto");
    limpiarFormulario(tipo);

    if (editando != null && editando.tipo == tipo) {
        editando = null;
    }
}

function cerrarTodosLosFormularios() {
    Object.keys(configuracion).forEach(function (tipo) {
        document.getElementById(configuracion[tipo].formulario).classList.add("oculto");
        document.getElementById(configuracion[tipo].idInput).disabled = false;
    });
}

/* Mostrar tablas */

function mostrarTabla(tipo, lista = null) {
    if (lista == null) {
        lista = datos[tipo];
    }

    let tabla = document.getElementById(configuracion[tipo].tabla);
    tabla.innerHTML = "";

    lista.forEach(function (registro) {
        let fila = document.createElement("tr");
        let valores = [];

        if (tipo == "peliculas") {
            valores = [
                registro.id,
                registro.titulo,
                registro.genero,
                registro.duracion + " min"
            ];
        }

        if (tipo == "usuarios") {
            valores = [
                registro.id,
                registro.nombre,
                registro.email
            ];
        }

        if (tipo == "sesiones") {
            valores = [
                registro.id,
                registro.idPelicula,
                formatearFecha(registro.fecha),
                registro.hora,
                registro.sala,
                formatearPrecio(registro.precio)
            ];
        }

        if (tipo == "reservas") {
            valores = [
                registro.id,
                registro.idUsuario,
                registro.idSesion,
                registro.asientos.join(", ")
            ];
        }

        valores.forEach(function (valor) {
            let celda = document.createElement("td");
            celda.textContent = valor;
            fila.appendChild(celda);
        });

        tabla.appendChild(fila);
    });
}

/* ACCIÓN */

function abrirAccion(tipo, accion) {
    tipoActual = tipo;
    accionActual = accion;
    registroAEliminar = null;

    let nombre = configuracion[tipo].singular;
    let titulo = "";

    if (accion == "buscar") {
        titulo = "Buscar " + nombre;
    }

    if (accion == "modificar") {
        titulo = "Modificar " + nombre;
    }

    if (accion == "eliminar") {
        titulo = "Eliminar " + nombre;
    }

    tituloAccion.textContent = capitalize(titulo);
    textoAccion.textContent = "Introduce el ID de " + articulo(tipo) + " que quieres " + accion + ".";
    labelIdAccion.textContent = "ID de " + nombre;

    idAccion.value = "";
    mensajeAccion.textContent = "";

    textoAccion.classList.remove("oculto");
    zonaIdAccion.classList.remove("oculto");
    botonesAccion.classList.remove("oculto");
    confirmacionEliminar.classList.add("oculto");

    ventanaAccion.classList.remove("oculto");

    idAccion.focus();
}

/* ACEPTAR ACCIÓN */

aceptarAccion.addEventListener("click", function () {
    let id = Number(idAccion.value);

    if (!Number.isInteger(id) || id <= 0) {
        mensajeAccion.textContent = "Introduce un ID válido";
        return;
    }

    let registro = datos[tipoActual].find(function (elemento) {
        return elemento.id == id;
    });

    if (registro == null) {
        mensajeAccion.textContent = "No existe " + articulo(tipoActual) + " con ese ID";
        return;
    }

    if (accionActual == "buscar") {
        let tipo = tipoActual;

        mostrarTabla(tipo, [registro]);
        cerrarAccion();

        mostrarMensaje(tipo, capitalize(configuracion[tipo].singular) + " encontrada/o");
    }

    if (accionActual == "modificar") {
        let tipo = tipoActual;

        cerrarAccion();
        abrirFormulario(tipo, registro);
    }

    if (accionActual == "eliminar") {
        registroAEliminar = registro;

        textoAccion.classList.add("oculto");
        zonaIdAccion.classList.add("oculto");
        botonesAccion.classList.add("oculto");
        mensajeAccion.textContent = "";

        textoConfirmacion.textContent =
            "¿Seguro que quieres eliminar " + obtenerNombreRegistro(tipoActual, registro) + "?";

        confirmacionEliminar.classList.remove("oculto");
    }
});

/* ENTER EN ACCIÓN */

idAccion.addEventListener("keydown", function (event) {
    if (event.key == "Enter") {
        aceptarAccion.click();
    }
});

/* ELIMINAR */

confirmarEliminar.addEventListener("click", function () {
    if (registroAEliminar == null) {
        return;
    }

    let tipo = tipoActual;
    let registro = registroAEliminar;

    let error = comprobarDependenciasAntesDeEliminar(tipo, registro.id);

    if (error != "") {
        cerrarAccion();
        mostrarMensaje(tipo, error);
        return;
    }

    let posicion = datos[tipo].findIndex(function (elemento) {
        return elemento.id == registro.id;
    });

    if (posicion != -1) {
        datos[tipo].splice(posicion, 1);
    }

    guardarDatos(tipo);
    mostrarTabla(tipo);
    cerrarAccion();

    mostrarMensaje(tipo, capitalize(configuracion[tipo].singular) + " eliminada/o correctamente");
});

/* DEPENDENCIAS */

function comprobarDependenciasAntesDeEliminar(tipo, id) {
    if (tipo == "peliculas") {
        let usada = datos.sesiones.some(function (sesion) {
            return sesion.idPelicula == id;
        });

        if (usada) {
            return "No puedes eliminar esta película porque tiene sesiones asociadas";
        }
    }

    if (tipo == "usuarios") {
        let usado = datos.reservas.some(function (reserva) {
            return reserva.idUsuario == id;
        });

        if (usado) {
            return "No puedes eliminar este usuario porque tiene reservas";
        }
    }

    if (tipo == "sesiones") {
        let usada = datos.reservas.some(function (reserva) {
            return reserva.idSesion == id;
        });

        if (usada) {
            return "No puedes eliminar esta sesión porque tiene reservas";
        }
    }

    return "";
}

/* CANCELAR ACCIÓN */

cancelarAccion.addEventListener("click", cerrarAccion);
cancelarEliminar.addEventListener("click", cerrarAccion);

ventanaAccion.addEventListener("click", function (event) {
    if (event.target == ventanaAccion) {
        cerrarAccion();
    }
});

document.addEventListener("keydown", function (event) {
    if (event.key == "Escape" && !ventanaAccion.classList.contains("oculto")) {
        cerrarAccion();
    }
});

function cerrarAccion() {
    ventanaAccion.classList.add("oculto");

    idAccion.value = "";
    mensajeAccion.textContent = "";

    textoAccion.classList.remove("oculto");
    zonaIdAccion.classList.remove("oculto");
    botonesAccion.classList.remove("oculto");
    confirmacionEliminar.classList.add("oculto");

    tipoActual = "";
    accionActual = "";
    registroAEliminar = null;
}

/* LOCALSTORAGE */

function cargarDatos(clave) {
    try {
        let guardado = localStorage.getItem(clave);

        if (guardado == null) {
            return [];
        }

        let convertido = JSON.parse(guardado);

        if (!Array.isArray(convertido)) {
            return [];
        }

        if (clave == "reservas") {
            convertido.forEach(function (reserva) {
                if (!Array.isArray(reserva.asientos)) {
                    reserva.asientos = leerAsientos(String(reserva.asientos));
                }
            });
        }

        return convertido;
    } catch (error) {
        return [];
    }
}

function guardarDatos(tipo) {
    localStorage.setItem(tipo, JSON.stringify(datos[tipo]));
}

function guardarTodo() {
    guardarDatos("peliculas");
    guardarDatos("usuarios");
    guardarDatos("sesiones");
    guardarDatos("reservas");
}

/* ASIENTOS */

function leerAsientos(texto) {
    if (texto.trim() == "") {
        return [];
    }

    return texto
        .split(/[,\s]+/)
        .filter(function (valor) {
            return valor != "";
        })
        .map(function (valor) {
            return Number(valor);
        });
}

/* MENSAJES */

function mostrarMensaje(tipo, texto) {
    document.getElementById(configuracion[tipo].mensaje).textContent = texto;
}

/* FORMATEAR */

function formatearFecha(fecha) {
    if (fecha == null || fecha == "") {
        return "";
    }

    let partes = fecha.split("-");

    if (partes.length != 3) {
        return fecha;
    }

    return partes[2] + "/" + partes[1] + "/" + partes[0];
}

function formatearPrecio(precio) {
    return Number(precio).toFixed(2).replace(".", ",") + " €";
}

function capitalize(texto) {
    if (texto == "") {
        return "";
    }

    return texto.charAt(0).toUpperCase() + texto.slice(1);
}

function articulo(tipo) {
    if (tipo == "peliculas") {
        return "una película";
    }

    if (tipo == "usuarios") {
        return "un usuario";
    }

    if (tipo == "sesiones") {
        return "una sesión";
    }

    return "una reserva";
}

function obtenerNombreRegistro(tipo, registro) {
    if (tipo == "peliculas") {
        return 'la película "' + registro.titulo + '"';
    }

    if (tipo == "usuarios") {
        return 'al usuario "' + registro.nombre + '"';
    }

    if (tipo == "sesiones") {
        return "la sesión con ID " + registro.id;
    }

    return "la reserva con ID " + registro.id;
}

/* Exportar JSON */

document.getElementById("exportarJSON").addEventListener("click", function () {
    let contenido = JSON.stringify(datos, null, 2);

    descargarArchivo(
        "cine.json",
        contenido,
        "application/json"
    );

    mensajeExportar("JSON exportado correctamente");
});

/* Leer JSON */

document.getElementById("leerJSON").addEventListener("click", function () {
    document.getElementById("archivoJSON").click();
});

document.getElementById("archivoJSON").addEventListener("change", async function (event) {
    let archivo = event.target.files[0];

    if (archivo == null) {
        return;
    }

    try {
        let texto = await archivo.text();
        let nuevosDatos = JSON.parse(texto);

        if (
            !Array.isArray(nuevosDatos.peliculas) ||
            !Array.isArray(nuevosDatos.usuarios) ||
            !Array.isArray(nuevosDatos.sesiones) ||
            !Array.isArray(nuevosDatos.reservas)
        ) {
            throw new Error();
        }

        nuevosDatos.reservas.forEach(function (reserva) {
            if (!Array.isArray(reserva.asientos)) {
                reserva.asientos = leerAsientos(String(reserva.asientos));
            }
        });

        datos = {
            peliculas: nuevosDatos.peliculas,
            usuarios: nuevosDatos.usuarios,
            sesiones: nuevosDatos.sesiones,
            reservas: nuevosDatos.reservas
        };

        guardarTodo();
        mostrarTodasLasTablas();

        mensajeExportar("JSON leído correctamente");
    } catch (error) {
        mensajeExportar("El archivo JSON no tiene un formato válido");
    }

    event.target.value = "";
});

/* XML tipo XSTREAM */

document.getElementById("exportarXStream").addEventListener("click", function () {
    let xml = '<?xml version="1.0" encoding="UTF-8"?>';
    xml += "<cine>";

    xml += "<peliculas>";

    datos.peliculas.forEach(function (p) {
        xml += "<pelicula>";
        xml += "<id>" + escaparXML(p.id) + "</id>";
        xml += "<titulo>" + escaparXML(p.titulo) + "</titulo>";
        xml += "<genero>" + escaparXML(p.genero) + "</genero>";
        xml += "<duracion>" + escaparXML(p.duracion) + "</duracion>";
        xml += "</pelicula>";
    });

    xml += "</peliculas>";

    xml += "<usuarios>";

    datos.usuarios.forEach(function (u) {
        xml += "<usuario>";
        xml += "<id>" + escaparXML(u.id) + "</id>";
        xml += "<nombre>" + escaparXML(u.nombre) + "</nombre>";
        xml += "<email>" + escaparXML(u.email) + "</email>";
        xml += "</usuario>";
    });

    xml += "</usuarios>";

    xml += "<sesiones>";

    datos.sesiones.forEach(function (s) {
        xml += "<sesion>";
        xml += "<id>" + escaparXML(s.id) + "</id>";
        xml += "<idPelicula>" + escaparXML(s.idPelicula) + "</idPelicula>";
        xml += "<fecha>" + escaparXML(s.fecha) + "</fecha>";
        xml += "<hora>" + escaparXML(s.hora) + "</hora>";
        xml += "<sala>" + escaparXML(s.sala) + "</sala>";
        xml += "<precio>" + escaparXML(s.precio) + "</precio>";
        xml += "</sesion>";
    });

    xml += "</sesiones>";

    xml += "<reservas>";

    datos.reservas.forEach(function (r) {
        xml += "<reserva>";
        xml += "<id>" + escaparXML(r.id) + "</id>";
        xml += "<idUsuario>" + escaparXML(r.idUsuario) + "</idUsuario>";
        xml += "<idSesion>" + escaparXML(r.idSesion) + "</idSesion>";
        xml += "<asientos>";

        r.asientos.forEach(function (asiento) {
            xml += "<asiento>" + escaparXML(asiento) + "</asiento>";
        });

        xml += "</asientos>";
        xml += "</reserva>";
    });

    xml += "</reservas>";
    xml += "</cine>";

    descargarArchivo("cine-xstream.xml", xml, "application/xml");

    mensajeExportar("XML exportado correctamente");
});

/* XML DOM */

document.getElementById("exportarDOM").addEventListener("click", function () {
    let documento = document.implementation.createDocument("", "cine", null);
    let raiz = documento.documentElement;

    crearListaXML(documento, raiz, "peliculas", "pelicula", datos.peliculas);
    crearListaXML(documento, raiz, "usuarios", "usuario", datos.usuarios);
    crearListaXML(documento, raiz, "sesiones", "sesion", datos.sesiones);
    crearListaXML(documento, raiz, "reservas", "reserva", datos.reservas);

    let xml =
        '<?xml version="1.0" encoding="UTF-8"?>' +
        new XMLSerializer().serializeToString(documento);

    descargarArchivo(
        "cine-dom.xml",
        xml,
        "application/xml"
    );

    mensajeExportar("XML DOM exportado correctamente");
});

function crearListaXML(documento, raiz, nombreLista, nombreElemento, lista) {
    let contenedor = documento.createElement(nombreLista);

    lista.forEach(function (registro) {
        let elemento = documento.createElement(nombreElemento);

        Object.keys(registro).forEach(function (clave) {
            if (clave == "asientos" && Array.isArray(registro[clave])) {
                let contenedorAsientos = documento.createElement("asientos");

                registro[clave].forEach(function (numero) {
                    let asiento = documento.createElement("asiento");
                    asiento.textContent = numero;
                    contenedorAsientos.appendChild(asiento);
                });

                elemento.appendChild(contenedorAsientos);
            } else {
                let campo = documento.createElement(clave);
                campo.textContent = registro[clave];
                elemento.appendChild(campo);
            }
        });

        contenedor.appendChild(elemento);
    });

    raiz.appendChild(contenedor);
}

function escaparXML(valor) {
    return String(valor)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&apos;");
}

/* Descargar archivos */

function descargarArchivo(nombre, contenido, tipo) {
    let blob = new Blob([contenido], {
        type: tipo
    });

    let url = URL.createObjectURL(blob);
    let enlace = document.createElement("a");

    enlace.href = url;
    enlace.download = nombre;

    document.body.appendChild(enlace);
    enlace.click();
    enlace.remove();

    URL.revokeObjectURL(url);
}

function mensajeExportar(texto) {
    document.getElementById("mensajeExportar").textContent = texto;
}

/* Inicio */

function mostrarTodasLasTablas() {
    mostrarTabla("peliculas");
    mostrarTabla("usuarios");
    mostrarTabla("sesiones");
    mostrarTabla("reservas");
}

mostrarTodasLasTablas();