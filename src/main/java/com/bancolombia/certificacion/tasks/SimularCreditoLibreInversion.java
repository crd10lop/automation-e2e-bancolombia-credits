package com.bancolombia.certificacion.tasks;

import com.bancolombia.certificacion.interactions.CerrarModalesIniciales;
import com.bancolombia.certificacion.interactions.EsperarBotonHabilitado;
import com.bancolombia.certificacion.interactions.EsperarElemento;
import com.bancolombia.certificacion.interactions.PausaVisible;
import com.bancolombia.certificacion.interactions.SeleccionarFechaCalendario;
import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.userinterfaces.SimuladorLibreInversionUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Tarea de negocio: simular un credito de libre inversion en el portal de Bancolombia.
// Reproduce el camino completo del usuario: cerrar los avisos iniciales, entrar al
// formulario desde la pantalla de bienvenida, confirmar que conoce el monto y diligenciar
// monto, plazo y fecha de nacimiento. Recibe los datos en un modelo para mantener
// las capas desacopladas.
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

        // Cerramos cookies y consentimiento, que de lo contrario tapan la pantalla.
        actor.attemptsTo(
            CerrarModalesIniciales.siAparecen()
        );

        // Entramos al formulario desde la pantalla de bienvenida del credito.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibreInversionUI.BOTON_CONTINUAR),
            PausaVisible.estandar(),
            Click.on(SimuladorLibreInversionUI.BOTON_CONTINUAR)
        );

        // Tras avanzar puede reaparecer algun aviso, asi que volvemos a cerrarlos.
        actor.attemptsTo(
            CerrarModalesIniciales.siAparecen()
        );

        // Confirmamos que el usuario ya sabe el monto. La opcion es un radio que el
        // banco oculta visualmente y reemplaza por una etiqueta, por eso no esperamos
        // a que sea "visible" sino que lo marcamos directamente por javascript.
        actor.attemptsTo(
            PausaVisible.estandar(),
            JavaScriptClick.on(SimuladorLibreInversionUI.OPCION_SI_MONTO)
        );

        // Ingresamos el valor del credito y el plazo en meses que se desea simular.
        actor.attemptsTo(
            EsperarElemento.seaVisible(SimuladorLibreInversionUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Enter.theValue(datos.getMonto()).into(SimuladorLibreInversionUI.CAMPO_MONTO),
            PausaVisible.estandar(),
            Enter.theValue(datos.getPlazo()).into(SimuladorLibreInversionUI.CAMPO_PLAZO)
        );

        // Elegimos la fecha de nacimiento con el calendario del banco, que es
        // obligatoria para que el simulador habilite el calculo de la cuota.
        actor.attemptsTo(
            SeleccionarFechaCalendario.en(
                SimuladorLibreInversionUI.CAMPO_FECHA_NACIMIENTO,
                datos.getFechaNacimiento()
            )
        );

        // Esperamos a que el banco habilite el boton y disparamos la simulacion.
        actor.attemptsTo(
            PausaVisible.estandar(),
            EsperarBotonHabilitado.elBoton(SimuladorLibreInversionUI.BOTON_SIMULAR),
            PausaVisible.estandar(),
            Click.on(SimuladorLibreInversionUI.BOTON_SIMULAR)
        );
    }
}
