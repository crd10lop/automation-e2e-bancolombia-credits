package com.bancolombia.certificacion.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

// Runner que conecta los escenarios Gherkin con las definiciones de pasos.
// La ruta features apunta a los archivos .feature del proyecto.
// La ruta glue apunta al paquete donde estan los StepDefinitions.
// El plugin pretty hace el reporte de consola legible durante la ejecucion.
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.bancolombia.certificacion.stepdefinitions",
    plugin = {"pretty"},
    tags = ""
)
public class SimuladoresCreditoRunner {
    // Esta clase permanece vacia. Su unica funcion es configurar el runner de Cucumber con Serenity.
}
