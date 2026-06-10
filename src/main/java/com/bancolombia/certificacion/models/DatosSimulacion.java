package com.bancolombia.certificacion.models;

// Representa los datos que el usuario ingresa en cualquier simulador de credito.
// Al centralizar los datos en un objeto, las Tasks reciben un solo parametro
// en lugar de multiples valores sueltos, lo que reduce el acoplamiento entre capas.
public class DatosSimulacion {

    // Monto en pesos que el usuario desea solicitar al banco
    private String monto;

    // Numero de meses en que el usuario desea pagar el credito
    private String plazo;

    // Fecha de nacimiento en formato YYYY-MM-DD, requerida solo en libranza.
    // El input type="date" del navegador exige este formato ISO 8601.
    // Ejemplo: 1990-06-15 para el 15 de junio de 1990.
    private String fechaNacimiento;

    public DatosSimulacion(String monto, String plazo) {
        this.monto = monto;
        this.plazo = plazo;
        this.fechaNacimiento = "";
    }

    public DatosSimulacion(String monto, String plazo, String fechaNacimiento) {
        this.monto = monto;
        this.plazo = plazo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getMonto() {
        return monto;
    }

    public String getPlazo() {
        return plazo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
}
