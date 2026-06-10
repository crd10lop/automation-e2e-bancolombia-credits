package com.bancolombia.certificacion.tasks;

import com.bancolombia.certificacion.interactions.EsperarElemento;
import com.bancolombia.certificacion.interactions.PausaVisible;
import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.userinterfaces.SimuladorLibreInversionUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Tarea de negocio: simular un credito de libre inversion en el portal de Bancolombia.
// Encapsula el flujo completo desde la pantalla de bienvenida hasta el ingreso de datos,
// siguiendo el orden que el banco impone: continuar -> confirmar monto -> ingresar valores.
// Esta tarea recibe el modelo con los datos para no depender de parametros sueltos.
public class SimularCreditoLibreInversion implements Task {

    private final DatosSimulacion datos;

    public SimularCreditoLibreInversion(DatosSimulacion datos) {
        this.datos = datos;
    }

    public static SimularCreditoLibreInversion con(DatosSimulacion datos) {
        return instrumented(SimularCreditoLibreInversion.class, datos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        // El banco muestra una pantalla introductoria con las condiciones del credito.
        // El usuario debe hacer clic en continuar para acceder al formulario de simulacion.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibreInversionUI.BOTON_CONTINUAR),
            PausaVisible.estandar(),
            Click.on(SimuladorLibreInversionUI.BOTON_CONTINUAR)
        );

        // El banco pregunta si el usuario ya sabe el monto que necesita.
        // Seleccionar "Si" activa el campo de ingreso de monto en el formulario.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibreInversionUI.OPCION_SI_MONTO),
            PausaVisible.estandar(),
            JavaScriptClick.on(SimuladorLibreInversionUI.OPCION_SI_MONTO)
        );

        // Se ingresa el valor del credito que el usuario desea solicitar.
        // El campo usa mascara de formato Angular, por eso se limpia antes de escribir.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibreInversionUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Click.on(SimuladorLibreInversionUI.CAMPO_MONTO),
            Enter.theValue(datos.getMonto()).into(SimuladorLibreInversionUI.CAMPO_MONTO)
        );

        // Se selecciona el plazo en meses para el que se desea calcular la cuota.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorLibreInversionUI.SELECTOR_PLAZO)
        );

        // Se dispara el calculo de la cuota estimada.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorLibreInversionUI.BOTON_SIMULAR)
        );
    }
}
