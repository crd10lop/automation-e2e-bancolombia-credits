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

    // URL directa al simulador de libranza para empleados
    // Este producto permite a los empleados descontar la cuota directamente del salario
    public static final String URL_SIMULADOR_LIBRANZA =
        "https://www.bancolombia.com/personas/creditos/consumo/libranza-para-empleados/simulador-libranza-empleados";

    // URL directa al simulador de Crediagil, el credito rotativo de Bancolombia
    // Permite al cliente conocer el cupo disponible y las condiciones del credito rotativo
    public static final String URL_SIMULADOR_CREDIAGIL =
        "https://www.bancolombia.com/personas/creditos/consumo/crediagil-credito-rotativo/simulador-crediagil";

    // Tiempo de espera en milisegundos entre pasos para facilitar la
    // observacion visual durante la socializacion del taller
    public static final int ESPERA_ENTRE_PASOS = 1500;

    // Tiempo de espera mas largo para que los elementos del simulador
    // terminen de cargar antes de interactuar con ellos
    public static final int ESPERA_CARGA_PAGINA = 3000;
}
