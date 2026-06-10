package com.bancolombia.certificacion.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

// Pregunta que verifica si el resultado de la simulacion es visible en pantalla.
// En Screenplay, las validaciones deben estar en Questions, no en Tasks.
// Esta clase es reutilizable para los tres simuladores porque todos
// muestran su resultado en un contenedor con estructura similar.
public class ResultadoSimulacion implements Question<Boolean> {

    private final Target elementoResultado;

    public ResultadoSimulacion(Target elementoResultado) {
        this.elementoResultado = elementoResultado;
    }

    public static ResultadoSimulacion estaVisible(Target elemento) {
        return new ResultadoSimulacion(elemento);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            actor.attemptsTo(
                WaitUntil.the(elementoResultado, isVisible()).forNoMoreThan(15).seconds()
            );
            return elementoResultado.resolveFor(actor).isVisible();
        } catch (Exception e) {
            // Si el elemento no aparece en el tiempo esperado, la simulacion no mostro resultado
            return false;
        }
    }
}
