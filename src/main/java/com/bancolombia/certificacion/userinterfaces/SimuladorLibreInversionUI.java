package com.bancolombia.certificacion.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

// Mapea cada elemento visible en el simulador de Libre Inversion de Bancolombia.
// Centralizar los localizadores aqui garantiza que si el equipo de Bancolombia
// cambia un id o clase, solo se edita esta clase y no toda la automatizacion.
public class SimuladorLibreInversionUI {

    // Boton de bienvenida que habilita el acceso al formulario del simulador.
    // El banco lo usa para mostrar las condiciones generales del credito antes de simular.
    public static final Target BOTON_CONTINUAR = Target.the("boton continuar libre inversion")
            .located(By.id("boton-seleccion-tarjeta"));

    // Opcion de radio que indica que el usuario ya sabe el monto exacto que necesita.
    // Seleccionarla activa el campo de ingreso de monto en el formulario.
    public static final Target OPCION_SI_MONTO = Target.the("opcion si sabe el monto")
            .located(By.id("opcion-si"));

    // Campo donde el usuario digita el valor del credito que desea solicitar.
    // El banco valida que sea entre 1.000.000 y 200.000.000 pesos colombianos.
    public static final Target CAMPO_MONTO = Target.the("campo monto libre inversion")
            .located(By.id("valor-monto"));

    // Selector de plazo en meses. El banco acepta entre 48 y 84 meses para este tipo de credito.
    // Se interactua mediante el valor del option en el select de Angular.
    public static final Target SELECTOR_PLAZO = Target.the("selector plazo libre inversion")
            .located(By.cssSelector("select[formcontrolname='plazo'], bc-select[formcontrolname='plazo']"));

    // Boton que dispara el calculo de la cuota estimada con los datos ingresados.
    public static final Target BOTON_SIMULAR = Target.the("boton simular libre inversion")
            .located(By.cssSelector("button[class*='bc-button-primary']"));

    // Contenedor donde el banco muestra el resultado con la cuota mensual aproximada.
    // Validar su presencia confirma que el simulador proceso los datos correctamente.
    public static final Target RESULTADO_SIMULACION = Target.the("resultado simulacion libre inversion")
            .located(By.cssSelector("bc-card-result, div[class*='resultado'], div[class*='result'], span[class*='cuota']"));
}
