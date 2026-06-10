package com.bancolombia.certificacion.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

// Mapea los elementos reales del simulador de Crediagil Bancolombia.
// Crediagil es un cupo rotativo y su formulario es mas corto que los demas:
// solo pide el monto a solicitar y la fecha de nacimiento del cliente.
// No maneja plazo, porque al ser rotativo el cliente usa y paga el cupo cuando quiere.
public class SimuladorCrediAgilUI {

    // Boton de bienvenida que da paso al formulario del cupo rotativo.
    public static final Target BOTON_CONTINUAR = Target.the("boton continuar crediagil")
            .located(By.id("boton-seleccion-tarjeta"));

    // Campo donde el cliente escribe el monto del cupo que quiere solicitar.
    public static final Target CAMPO_MONTO = Target.the("campo monto crediagil")
            .located(By.id("valor-simulacion"));

    // Campo de fecha de nacimiento que se diligencia con el calendario del banco.
    // Se expone como localizador directo porque el calendario requiere el clic
    // nativo del navegador sobre el elemento para desplegarse.
    public static final By CAMPO_FECHA_NACIMIENTO = By.id("03");

    // Boton que ejecuta el calculo del cupo. Comienza deshabilitado y se habilita
    // cuando el monto y la fecha de nacimiento son validos.
    public static final Target BOTON_SIMULAR = Target.the("boton simular crediagil")
            .located(By.id("boton-simular"));

    // Zona donde el banco muestra el resultado del cupo rotativo simulado.
    // Tambien damos por valida la simulacion si aparece el reCAPTCHA "No soy robot",
    // porque significa que el formulario se diligencio y se envio al banco; esa
    // verificacion manual queda fuera del alcance de la prueba automatizada.
    public static final Target RESULTADO_SIMULACION = Target.the("resultado simulacion crediagil")
            .located(By.cssSelector(".numero-cuotas, .cbc-pay, .cbc-part-card, [class*='resultado'], "
                    + "iframe[src*='recaptcha'], iframe[title*='reCAPTCHA'], .g-recaptcha"));
}
