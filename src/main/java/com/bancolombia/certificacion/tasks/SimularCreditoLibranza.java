package com.bancolombia.certificacion.tasks;

import com.bancolombia.certificacion.interactions.CerrarModalesIniciales;
import com.bancolombia.certificacion.interactions.EsperarBotonHabilitado;
import com.bancolombia.certificacion.interactions.EsperarElemento;
import com.bancolombia.certificacion.interactions.PausaVisible;
import com.bancolombia.certificacion.interactions.SeleccionarFechaCalendario;
import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.userinterfaces.SimuladorLibranzaUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Tarea de negocio: simular un credito de libranza en el portal de Bancolombia.
// Este simulador muestra el formulario directamente, sin pantalla de bienvenida.
// El plazo es un campo de texto en meses y la fecha de nacimiento se elige con
// el calendario del banco, que es lo que valida la edad y habilita la simulacion.
public class SimularCreditoLibranza implements Task {

    private final DatosSimulacion datos;

    public SimularCreditoLibranza(DatosSimulacion datos) {
        this.datos = datos;
    }

    public static SimularCreditoLibranza con(DatosSimulacion datos) {
        return instrumented(SimularCreditoLibranza.class, datos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Cerramos cookies y consentimiento para poder usar el formulario.
        actor.attemptsTo(
            CerrarModalesIniciales.siAparecen()
        );

        // Ingresamos el valor del credito y el plazo en meses.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibranzaUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Enter.theValue(datos.getMonto()).into(SimuladorLibranzaUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Enter.theValue(datos.getPlazo()).into(SimuladorLibranzaUI.CAMPO_PLAZO)
        );

        // Elegimos la fecha de nacimiento con el calendario para validar la edad
        // y permitir el calculo del seguro de vida del credito.
        actor.attemptsTo(
            SeleccionarFechaCalendario.en(
                SimuladorLibranzaUI.CAMPO_FECHA_NACIMIENTO,
                datos.getFechaNacimiento()
            )
        );

        // Esperamos a que el banco habilite el boton y ejecutamos la simulacion.
        actor.attemptsTo(
            PausaVisible.estandar(),
            EsperarBotonHabilitado.elBoton(SimuladorLibranzaUI.BOTON_SIMULAR),
            PausaVisible.estandar(),
            Click.on(SimuladorLibranzaUI.BOTON_SIMULAR)
        );
    }
}
