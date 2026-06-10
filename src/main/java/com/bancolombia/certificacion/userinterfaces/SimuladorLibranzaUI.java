package com.bancolombia.certificacion.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

// Mapea los elementos del simulador de Libranza Bancolombia.
// Este simulador difiere del de libre inversion porque muestra directamente
// los campos sin pantalla de bienvenida ni pregunta previa de monto.
// Adicionalmente requiere la fecha de nacimiento del solicitante.
public class SimuladorLibranzaUI {

    // Campo para ingresar el monto del credito de libranza.
    // El banco descuenta la cuota directamente del salario del empleado.
    public static final Target CAMPO_MONTO = Target.the("campo monto libranza")
            .located(By.cssSelector("input[id*='monto'], input[formcontrolname='monto'], bc-input input[placeholder='0']"));

    // Campo para ingresar el numero de meses del credito de libranza.
    // El rango permitido va de 72 a 96 meses segun las condiciones del producto.
    public static final Target CAMPO_PLAZO = Target.the("campo plazo libranza")
            .located(By.cssSelector("input[id*='plazo'], input[formcontrolname='plazo'], bc-input input[placeholder*='mes']"));

    // Campo de fecha de nacimiento requerido para validar la edad del solicitante.
    // El banco acepta unicamente solicitantes entre 18 y 70 anos.
    public static final Target CAMPO_FECHA_NACIMIENTO = Target.the("campo fecha nacimiento libranza")
            .located(By.cssSelector("input[id*='fecha'], input[formcontrolname*='fecha'], bc-datepicker input"));

    // Boton que ejecuta el calculo de la cuota estimada del credito de libranza.
    public static final Target BOTON_SIMULAR = Target.the("boton simular libranza")
            .located(By.cssSelector("button[class*='bc-button-primary']"));

    // Contenedor donde se muestra la cuota mensual calculada para el credito de libranza.
    // Su presencia en el DOM confirma que el simulador proceso exitosamente los datos.
    public static final Target RESULTADO_SIMULACION = Target.the("resultado simulacion libranza")
            .located(By.cssSelector("bc-card-result, div[class*='cuota'], span[class*='cuota'], p[class*='result']"));
}
