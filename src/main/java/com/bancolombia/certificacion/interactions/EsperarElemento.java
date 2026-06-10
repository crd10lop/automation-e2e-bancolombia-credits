package com.bancolombia.certificacion.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

// Encapsula la espera explicita antes de interactuar con un elemento Angular.
// Los simuladores de Bancolombia cargan sus componentes de forma asincrona,
// por lo que es necesario esperar a que el elemento sea visible antes de actuar.
// Usar WaitUntil en lugar de Thread.sleep es la practica correcta en Serenity:
// no paraliza el hilo, sino que reintenta la condicion hasta cumplirla o agotar el tiempo.
public class EsperarElemento implements Interaction {

    private final Target elementoObjetivo;

    public EsperarElemento(Target elementoObjetivo) {
        this.elementoObjetivo = elementoObjetivo;
    }

    public static EsperarElemento seaVisible(Target elemento) {
        return new EsperarElemento(elemento);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(elementoObjetivo, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
