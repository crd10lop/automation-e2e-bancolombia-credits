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
// Cada metodo hace una sola cosa: o delega a una tarea o evalua una pregunta.
// La logica de automatizacion nunca vive aqui sino en Tasks, Interactions y Questions.
public class SimuladoresCreditoSteps {

    @Managed
    private WebDriver navegador;

    // El actor representa al usuario que interactua con los simuladores del banco.
    // Se inicializa una sola vez por escenario gracias a la anotacion @Before.
    private Actor usuarioBancolombia;

    // El simulador de libranza recibe sus datos en varios pasos del feature.
    // Guardamos monto, plazo y fecha aqui para construir el modelo cuando
    // el usuario finalmente solicita simular el credito.
    private String montoLibranza;
    private String plazoLibranza;
    private String fechaNacimientoLibranza;

    @Before
    public void configurarActor() {
        usuarioBancolombia = Actor.named("Usuario Bancolombia")
                .can(BrowseTheWeb.with(navegador));
    }

    @Dado("que el usuario se encuentra en el portal de creditos de Bancolombia")
    public void usuarioEnPortalCreditos() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_BASE)
        );
    }

    // --- Pasos para Libre Inversion ---

    @Cuando("el usuario navega al simulador de libre inversion")
    public void navegarSimuladorLibreInversion() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_SIMULADOR_LIBRE_INVERSION)
        );
    }

    @Cuando("acepta continuar con la simulacion de libre inversion")
    public void aceptarContinuarLibreInversion() {
        // El click en continuar y la seleccion del Si se delegan a la tarea principal.
        // Este paso existe para que el feature sea legible como lenguaje de negocio.
    }

    @Cuando("selecciona que si sabe el monto que necesita")
    public void seleccionarSiMontoLibreInversion() {
        // La interaccion con el radio button ocurre dentro de la tarea SimularCreditoLibreInversion.
    }

    @Cuando("ingresa un monto de {string} y un plazo de {string} meses para libre inversion")
    public void ingresarDatosLibreInversion(String monto, String plazo) {
        DatosSimulacion datos = new DatosSimulacion(monto, plazo);
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

    // --- Pasos para Libranza ---

    @Cuando("el usuario navega al simulador de libranza")
    public void navegarSimuladorLibranza() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_SIMULADOR_LIBRANZA)
        );
    }

    @Cuando("ingresa un monto de {string} y un plazo de {string} meses para libranza")
    public void ingresarMontoPlazoPorLibranza(String monto, String plazo) {
        // Guardamos monto y plazo para reunirlos con la fecha de nacimiento.
        // El simulador de libranza necesita los tres datos juntos al momento de simular.
        this.montoLibranza = monto;
        this.plazoLibranza = plazo;
    }

    @Cuando("ingresa la fecha de nacimiento {string} para libranza")
    public void ingresarFechaNacimientoLibranza(String fechaNacimiento) {
        // Completamos el ultimo dato que el banco exige para calcular el seguro de vida.
        this.fechaNacimientoLibranza = fechaNacimiento;
    }

    @Cuando("hace clic en simular para libranza")
    public void simularLibranza() {
        // Con todos los datos reunidos construimos el modelo y disparamos la simulacion.
        DatosSimulacion datos = new DatosSimulacion(montoLibranza, plazoLibranza, fechaNacimientoLibranza);
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

    // --- Pasos para Crediagil ---

    @Cuando("el usuario navega al simulador de Crediagil")
    public void navegarSimuladorCrediagil() {
        usuarioBancolombia.attemptsTo(
            Open.url(Constantes.URL_SIMULADOR_CREDIAGIL)
        );
    }

    @Cuando("acepta continuar con la simulacion de Crediagil")
    public void aceptarContinuarCrediagil() {
        // Delega al flujo interno de la tarea SimularCreditoCrediAgil.
    }

    @Cuando("selecciona que si sabe el monto que necesita en Crediagil")
    public void seleccionarSiMontoCrediagil() {
        // Delega al flujo interno de la tarea SimularCreditoCrediAgil.
    }

    @Cuando("ingresa un monto de {string} y un plazo de {string} meses para Crediagil")
    public void ingresarDatosCrediagil(String monto, String plazo) {
        DatosSimulacion datos = new DatosSimulacion(monto, plazo);
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
