package com.bancolombia.certificacion.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

// Mapea los elementos reales del simulador de Libranza Bancolombia.
// A diferencia de los otros simuladores, este no tiene pantalla de bienvenida:
// muestra de una vez el formulario con monto, plazo (en meses) y fecha de nacimiento.
// La libranza descuenta la cuota directamente del salario, por eso pide la fecha
// para validar la edad y calcular el seguro de vida obligatorio.
public class SimuladorLibranzaUI {

    // Campo donde el empleado escribe el valor del credito que necesita.
    public static final Target CAMPO_MONTO = Target.the("campo monto libranza")
            .located(By.id("totalAmount"));

    // Campo de texto para el numero de meses del credito de libranza.
    // El banco permite plazos largos (hasta 96 meses) por el menor riesgo de la nomina.
    public static final Target CAMPO_PLAZO = Target.the("campo plazo libranza")
            .located(By.id("month"));

    // Campo de fecha de nacimiento que se diligencia con el calendario del banco.
    // Se expone como localizador directo porque el calendario requiere el clic
    // nativo del navegador sobre el elemento para desplegarse.
    public static final By CAMPO_FECHA_NACIMIENTO = By.id("birthDate");

    // Boton Simular del formulario. No tiene id propio, por eso se ubica por su texto.
    // Comienza deshabilitado y se habilita cuando los tres campos son validos.
    public static final Target BOTON_SIMULAR = Target.the("boton simular libranza")
            .located(By.xpath("//button[contains(@class,'bc-button-primary')][normalize-space()='Simular']"));

    // Tarjeta donde el banco muestra la cuota aproximada del credito de libranza.
    // Su visibilidad confirma que el simulador proceso los datos ingresados.
    public static final Target RESULTADO_SIMULACION = Target.the("resultado simulacion libranza")
            .located(By.cssSelector(".cbc-pay, .cbc-part-card"));
}
