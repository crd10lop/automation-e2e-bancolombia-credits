package com.bancolombia.certificacion.tasks;

import com.bancolombia.certificacion.interactions.CerrarModalesIniciales;
import com.bancolombia.certificacion.interactions.EsperarBotonHabilitado;
import com.bancolombia.certificacion.interactions.EsperarElemento;
import com.bancolombia.certificacion.interactions.PausaVisible;
import com.bancolombia.certificacion.interactions.SeleccionarFechaCalendario;
import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.userinterfaces.SimuladorCrediAgilUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Tarea de negocio: simular el cupo rotativo Crediagil en el portal de Bancolombia.
// Por ser un cupo rotativo, el formulario es corto: solo pide el monto que se quiere
// solicitar y la fecha de nacimiento del cliente, sin plazo. Se entra al formulario
// desde la pantalla de bienvenida y la fecha se elige con el calendario del banco.
public class SimularCreditoCrediAgil implements Task {

    private final DatosSimulacion datos;

    public SimularCreditoCrediAgil(DatosSimulacion datos) {
        this.datos = datos;
    }

    public static SimularCreditoCrediAgil con(DatosSimulacion datos) {
        return instrumented(SimularCreditoCrediAgil.class, datos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Cerramos cookies y consentimiento antes de empezar.
        actor.attemptsTo(
            CerrarModalesIniciales.siAparecen()
        );

        // Entramos al formulario desde la pantalla de bienvenida del cupo rotativo.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorCrediAgilUI.BOTON_CONTINUAR),
            PausaVisible.estandar(),
            Click.on(SimuladorCrediAgilUI.BOTON_CONTINUAR)
        );

        // Pueden reaparecer avisos al avanzar, asi que volvemos a cerrarlos.
        actor.attemptsTo(
            CerrarModalesIniciales.siAparecen()
        );

        // Ingresamos el monto del cupo que el cliente desea solicitar.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorCrediAgilUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Enter.theValue(datos.getMonto()).into(SimuladorCrediAgilUI.CAMPO_MONTO)
        );

        // Elegimos la fecha de nacimiento con el calendario del banco.
        actor.attemptsTo(
            SeleccionarFechaCalendario.en(
                SimuladorCrediAgilUI.CAMPO_FECHA_NACIMIENTO,
                datos.getFechaNacimiento()
            )
        );

        // Esperamos a que el banco habilite el boton y disparamos la simulacion del cupo.
        actor.attemptsTo(
            PausaVisible.estandar(),
            EsperarBotonHabilitado.elBoton(SimuladorCrediAgilUI.BOTON_SIMULAR),
            PausaVisible.estandar(),
            Click.on(SimuladorCrediAgilUI.BOTON_SIMULAR)
        );
    }
}
