package com.bancolombia.certificacion.tasks;

import com.bancolombia.certificacion.interactions.EsperarElemento;
import com.bancolombia.certificacion.interactions.PausaVisible;
import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.userinterfaces.SimuladorCrediAgilUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Tarea de negocio: simular un credito Crediagil en el portal de Bancolombia.
// Crediagil es un cupo rotativo: se solicita una vez y se usa cuando se necesite.
// Su flujo de simulacion es identico al de libre inversion pero aplica
// restricciones distintas: ingresos minimos de 2.847.000 pesos y plazos de hasta 60 meses.
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

        // Pantalla introductoria del simulador de Crediagil con condiciones del producto.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorCrediAgilUI.BOTON_CONTINUAR),
            PausaVisible.estandar(),
            Click.on(SimuladorCrediAgilUI.BOTON_CONTINUAR)
        );

        // El usuario confirma que conoce el monto del cupo que desea simular.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorCrediAgilUI.OPCION_SI_MONTO),
            PausaVisible.estandar(),
            JavaScriptClick.on(SimuladorCrediAgilUI.OPCION_SI_MONTO)
        );

        // Se ingresa el valor del cupo rotativo que el usuario desea solicitar.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorCrediAgilUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Click.on(SimuladorCrediAgilUI.CAMPO_MONTO),
            Enter.theValue(datos.getMonto()).into(SimuladorCrediAgilUI.CAMPO_MONTO)
        );

        // Se selecciona el plazo para el calculo de la cuota del cupo rotativo.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorCrediAgilUI.SELECTOR_PLAZO)
        );

        // Se dispara el calculo de la cuota estimada del Crediagil.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorCrediAgilUI.BOTON_SIMULAR)
        );
    }
}
