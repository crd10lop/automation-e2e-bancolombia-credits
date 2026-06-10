package com.bancolombia.certificacion.utils;

// Centraliza las URLs y tiempos de espera del proyecto.
// Tener estos valores aqui evita duplicarlos en cada tarea o interaccion.
public class Constantes {

    private Constantes() {
        // Clase de utilidad, no debe instanciarse
    }

    // URL base del portal de creditos de Bancolombia
    public static final String URL_BASE = "https://www.bancolombia.com/personas/creditos";

    // URL directa al simulador de credito de libre inversion
    public static final String URL_SIMULADOR_LIBRE_INVERSION =
        "https://www.bancolombia.com/personas/creditos/consumo/credito-libre-inversion/simulador-libre-inversion";

    // URL directa al simulador de credito de vivienda
    public static final String URL_SIMULADOR_VIVIENDA =
        "https://www.bancolombia.com/personas/creditos/vivienda/simulador-credito-vivienda";

    // URL directa al simulador de credito para vehiculo (portal Sufi)
    public static final String URL_SIMULADOR_VEHICULO =
        "https://sufi.grupobancolombia.com/sufi/creditos/movilidad/vehiculo/simulador";

    // URL directa al simulador de credito educativo (portal Sufi)
    public static final String URL_SIMULADOR_EDUCATIVO =
        "https://sufi.grupobancolombia.com/sufi/creditos/educacion/credito-educativo/simulador";

    // Tiempo de espera en milisegundos entre pasos para facilitar la
    // observacion visual durante la socializacion del taller
    public static final int ESPERA_ENTRE_PASOS = 1500;

    // Tiempo de espera mas largo para que los iframes del simulador
    // terminen de cargar antes de interactuar con sus elementos
    public static final int ESPERA_CARGA_IFRAME = 3000;
}
