package com.bancolombia.certificacion.models;

// Representa los datos que el usuario ingresa en cualquier simulador de credito.
// Al recibir los datos como un objeto, las Tasks no necesitan multiples parametros sueltos,
// lo que reduce el acoplamiento entre capas y facilita agregar nuevos campos en el futuro.
public class DatosSimulacion {

    // Monto en pesos que el usuario desea solicitar al banco
    private String monto;

    // Numero de meses en que el usuario desea pagar el credito
    private String plazo;

    // Fecha de nacimiento requerida unicamente en el simulador de libranza
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
