package com.bancolombia.certificacion.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

// Mapea los elementos reales del simulador de Libre Inversion de Bancolombia.
// El flujo del banco es: pantalla de bienvenida con boton continuar, una pregunta
// para confirmar que el usuario sabe el monto, y luego el formulario con monto,
// plazo y fecha de nacimiento. La fecha es obligatoria porque el banco calcula
// el seguro de vida y valida la edad del solicitante.
public class SimuladorLibreInversionUI {

    // Boton de bienvenida que da paso al formulario de simulacion.
    public static final Target BOTON_CONTINUAR = Target.the("boton continuar libre inversion")
            .located(By.id("boton-seleccion-tarjeta"));

    // Opcion que indica que el usuario ya conoce el monto que necesita.
    // Al elegirla, el banco habilita el campo para escribir el valor del credito.
    public static final Target OPCION_SI_MONTO = Target.the("opcion si sabe el monto")
            .located(By.id("opcion-si"));

    // Campo donde se escribe el valor del credito que se desea solicitar.
    public static final Target CAMPO_MONTO = Target.the("campo monto libre inversion")
            .located(By.id("valor-monto"));

    // Campo de texto para el plazo en meses. El banco acepta de 48 a 84 meses.
    public static final Target CAMPO_PLAZO = Target.the("campo plazo libre inversion")
            .located(By.id("valor-plazo"));

    // Campo de fecha de nacimiento que se diligencia con el calendario del banco.
    // Se expone como localizador directo porque el calendario requiere el clic
    // nativo del navegador sobre el elemento para desplegarse.
    public static final By CAMPO_FECHA_NACIMIENTO = By.id("input-fecha");

    // Boton que ejecuta el calculo de la cuota. Permanece deshabilitado hasta
    // que el monto, el plazo y la fecha esten completos y sean validos.
    public static final Target BOTON_SIMULAR = Target.the("boton simular libre inversion")
            .located(By.id("boton-simular"));

    // Zona donde el banco muestra las cuotas calculadas para los distintos plazos.
    // Que sea visible confirma que la simulacion se proceso correctamente.
    // Tambien damos por valida la simulacion si aparece el reCAPTCHA "No soy robot",
    // porque significa que el formulario se diligencio y se envio al banco; esa
    // verificacion manual queda fuera del alcance de la prueba automatizada.
    public static final Target RESULTADO_SIMULACION = Target.the("resultado simulacion libre inversion")
            .located(By.cssSelector(".numero-cuotas, .resultados-simulacion-boton, .cbc-pay, "
                    + "iframe[src*='recaptcha'], iframe[title*='reCAPTCHA'], .g-recaptcha"));
}
