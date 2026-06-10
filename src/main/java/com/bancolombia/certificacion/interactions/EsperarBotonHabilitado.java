package com.bancolombia.certificacion.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

// Espera a que un boton que comienza deshabilitado se habilite.
// El boton Simular de Libranza permanece con el atributo disabled mientras
// los campos obligatorios no esten completos y validos.
// El banco lo habilita automaticamente via Angular cuando todos los datos son correctos.
public class EsperarBotonHabilitado implements Interaction {

    private final Target boton;

    public EsperarBotonHabilitado(Target boton) {
        this.boton = boton;
    }

    public static EsperarBotonHabilitado elBoton(Target boton) {
        return new EsperarBotonHabilitado(boton);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(boton, isEnabled()).forNoMoreThan(15).seconds()
        );
    }
}
