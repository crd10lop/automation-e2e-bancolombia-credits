package com.bancolombia.certificacion.stepdefinitions;

import com.bancolombia.certificacion.models.DatosSimulacion;
import com.bancolombia.certificacion.questions.ResultadoSimulacion;
import com.bancolombia.certificacion.tasks.SimularCreditoCrediAgil;
import com.bancolombia.certificacion.tasks.SimularCreditoLibranza;
import com.bancolombia.certificacion.tasks.SimularCreditoLibreInversion;
import com.bancolombia.certificacion.userinterfaces.SimuladorCrediAgilUI;
import com.bancolombia.certificacion.userinterfaces.SimuladorLibranzaUI;
import com.bancolombia.certificacion.userinterfaces.SimuladorLibreInversionUI;
import com.bancolombia.certificacion.utils.Constantes;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

// Puente entre los pasos escritos en Gherkin y las tareas del patron Screenplay.
// Cada metodo hace una sola cosa: abrir una pagina, delegar en una tarea o evaluar
// una pregunta. La logica de automatizacion vive en Tasks, Interactions y Questions.
public class SimuladoresCreditoSteps {

    @Managed
    private WebDriver navegador;

    // El actor representa al usuario que interactua con los simuladores del banco.
    private Actor usuarioBancolombia;

    @Before
    public void configurarActor() {
        // Descargamos el chromedriver que corresponde a la version de Chrome de esta
        // maquina y dejamos lista su ruta antes de abrir el navegador. Asi el proyecto
        // funciona en cualquier equipo sin tener que instalar el driver manualmente.
        WebDriverManager.chromedriver().setup();

        usuarioBancolombia = Actor.named("Usuario Bancolombia")
                .can(BrowseTheWeb.with(navegador));
    }

    @Dado("que el usuario se encuentra en el portal de creditos de Bancolombia")
    public void usuarioEnPortalCreditos() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_BASE)
        );
    }

    // --- Libre Inversion ---

    @Cuando("el usuario navega al simulador de libre inversion")
    public void navegarSimuladorLibreInversion() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_SIMULADOR_LIBRE_INVERSION)
        );
    }

    @Cuando("simula un credito de libre inversion con un monto de {string}, un plazo de {string} meses y fecha de nacimiento {string}")
    public void simularLibreInversion(String monto, String plazo, String fechaNacimiento) {
        DatosSimulacion datos = new DatosSimulacion(monto, plazo, fechaNacimiento);
        usuarioBancolombia.attemptsTo(
            SimularCreditoLibreInversion.con(datos)
        );
    }

    @Entonces("el resultado de la simulacion de libre inversion debe ser visible")
    public void verificarResultadoLibreInversion() {
        usuarioBancolombia.should(
            seeThat(ResultadoSimulacion.estaVisible(SimuladorLibreInversionUI.RESULTADO_SIMULACION), is(true))
        );
    }

    // --- Libranza ---

    @Cuando("el usuario navega al simulador de libranza")
    public void navegarSimuladorLibranza() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_SIMULADOR_LIBRANZA)
        );
    }

    @Cuando("simula un credito de libranza con un monto de {string}, un plazo de {string} meses y fecha de nacimiento {string}")
    public void simularLibranza(String monto, String plazo, String fechaNacimiento) {
        DatosSimulacion datos = new DatosSimulacion(monto, plazo, fechaNacimiento);
        usuarioBancolombia.attemptsTo(
            SimularCreditoLibranza.con(datos)
        );
    }

    @Entonces("el resultado de la simulacion de libranza debe ser visible")
    public void verificarResultadoLibranza() {
        usuarioBancolombia.should(
            seeThat(ResultadoSimulacion.estaVisible(SimuladorLibranzaUI.RESULTADO_SIMULACION), is(true))
        );
    }

    // --- Crediagil ---

    @Cuando("el usuario navega al simulador de Crediagil")
    public void navegarSimuladorCrediagil() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_SIMULADOR_CREDIAGIL)
        );
    }

    @Cuando("simula un cupo Crediagil con un monto de {string} y fecha de nacimiento {string}")
    public void simularCrediagil(String monto, String fechaNacimiento) {
        // Crediagil no maneja plazo por ser un cupo rotativo, por eso el plazo va vacio.
        DatosSimulacion datos = new DatosSimulacion(monto, "", fechaNacimiento);
        usuarioBancolombia.attemptsTo(
            SimularCreditoCrediAgil.con(datos)
        );
    }

    @Entonces("el resultado de la simulacion de Crediagil debe ser visible")
    public void verificarResultadoCrediagil() {
        usuarioBancolombia.should(
            seeThat(ResultadoSimulacion.estaVisible(SimuladorCrediAgilUI.RESULTADO_SIMULACION), is(true))
        );
    }
}
