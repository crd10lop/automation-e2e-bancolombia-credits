package com.bancolombia.certificacion.interactions;

import com.bancolombia.certificacion.utils.Constantes;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

// Cierra las ventanas emergentes que Bancolombia muestra al entrar a sus simuladores:
// el aviso de cookies y el modal de consentimiento de datos. Mientras esas capas
// esten abiertas tapan el formulario y el usuario no puede interactuar con los campos,
// por eso es lo primero que hacemos al llegar a cada simulador.
// Cerramos solo lo que realmente este visible para no afectar otros elementos de la pagina.
public class CerrarModalesIniciales implements Interaction {

    public static CerrarModalesIniciales siAparecen() {
        return new CerrarModalesIniciales();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver navegador = BrowseTheWeb.as(actor).getDriver();

        // Aceptamos las cookies y cerramos el modal de consentimiento solo si estan presentes.
        // Tambien confirmamos el aviso inicial de algunos simuladores (boton "Aceptar" pequeno).
        String cerrarModales =
            "['acceptCookies','modalConsentimientoDigital_primaryButton'].forEach(function(id){"
          + "  var e = document.getElementById(id);"
          + "  if (e && e.offsetParent !== null) { e.click(); }"
          + "});"
          + "document.querySelectorAll('button.bc-button-small').forEach(function(b){"
          + "  if (b.innerText && b.innerText.trim() === 'Aceptar' && b.offsetParent !== null) { b.click(); }"
          + "});";

        ((JavascriptExecutor) navegador).executeScript(cerrarModales);

        // Pequena espera para que el cierre de los modales termine antes de seguir.
        try {
            Thread.sleep(Constantes.ESPERA_ENTRE_PASOS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
