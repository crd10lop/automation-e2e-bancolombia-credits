package com.bancolombia.certificacion.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

// Mapea los elementos del simulador de Crediagil Bancolombia.
// Crediagil es un credito rotativo que sigue el mismo flujo que libre inversion:
// pantalla de bienvenida con boton continuar, pregunta de monto y luego el formulario.
public class SimuladorCrediAgilUI {

    // Boton de bienvenida del simulador de Crediagil.
    // Funciona igual que en libre inversion: acepta las condiciones antes de simular.
    public static final Target BOTON_CONTINUAR = Target.the("boton continuar crediagil")
            .located(By.id("boton-seleccion-tarjeta"));

    // Opcion de radio para indicar que el usuario conoce el monto que necesita.
    // Comparte el mismo id que libre inversion por ser la misma aplicacion Angular.
    public static final Target OPCION_SI_MONTO = Target.the("opcion si sabe el monto crediagil")
            .located(By.id("opcion-si"));

    // Campo de ingreso del monto para el credito rotativo Crediagil.
    // El banco exige que el solicitante tenga ingresos superiores a 2.847.000 pesos.
    public static final Target CAMPO_MONTO = Target.the("campo monto crediagil")
            .located(By.id("valor-monto"));

    // Selector del plazo en meses para Crediagil.
    // Permite 12, 24, 36 o 60 meses segun el canal de solicitud.
    public static final Target SELECTOR_PLAZO = Target.the("selector plazo crediagil")
            .located(By.cssSelector("select[formcontrolname='plazo'], bc-select[formcontrolname='plazo']"));

    // Boton que procesa el calculo de la cuota para el credito rotativo.
    public static final Target BOTON_SIMULAR = Target.the("boton simular crediagil")
            .located(By.cssSelector("button[class*='bc-button-primary']"));

    // Contenedor del resultado de la simulacion de Crediagil.
    // Verificar su visibilidad confirma que el flujo completo se ejecuto correctamente.
    public static final Target RESULTADO_SIMULACION = Target.the("resultado simulacion crediagil")
            .located(By.cssSelector("bc-card-result, div[class*='resultado'], div[class*='result'], span[class*='cuota']"));
}
