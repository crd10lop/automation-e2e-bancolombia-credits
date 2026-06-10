package com.bancolombia.certificacion.tasks;

import com.bancolombia.certificacion.interactions.EsperarElemento;
import com.bancolombia.certificacion.interactions.PausaVisible;
import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.userinterfaces.SimuladorLibranzaUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Tarea de negocio: simular un credito de libranza en el portal de Bancolombia.
// La libranza descuenta la cuota directamente del salario del empleado, por eso
// el simulador requiere adicionalmente la fecha de nacimiento del solicitante
// para validar que cumpla con el rango de edad permitido por el banco.
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

        // Se ingresa el valor del credito de libranza que el empleado desea solicitar.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibranzaUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Click.on(SimuladorLibranzaUI.CAMPO_MONTO),
            Enter.theValue(datos.getMonto()).into(SimuladorLibranzaUI.CAMPO_MONTO)
        );

        // Se ingresa el plazo en meses. La libranza permite plazos mayores porque
        // el riesgo del banco es menor al tener descuento directo de nomina.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorLibranzaUI.CAMPO_PLAZO),
            Enter.theValue(datos.getPlazo()).into(SimuladorLibranzaUI.CAMPO_PLAZO)
        );

        // La fecha de nacimiento permite al banco calcular el seguro de vida
        // y verificar que el credito venza antes de que el empleado cumpla 70 anos.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorLibranzaUI.CAMPO_FECHA_NACIMIENTO),
            Enter.theValue(datos.getFechaNacimiento()).into(SimuladorLibranzaUI.CAMPO_FECHA_NACIMIENTO)
        );

        // Se ejecuta el calculo de la cuota estimada con todos los datos ingresados.
        actor.attemptsTo(
            PausaVisible.estandar(),
            Click.on(SimuladorLibranzaUI.BOTON_SIMULAR)
        );
    }
}
