package com.bancolombia.certificacion.interactions;

import com.bancolombia.certificacion.utils.Constantes;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

// Introduce una pausa controlada entre pasos de la automatizacion.
// Esto NO reemplaza las esperas inteligentes de Serenity para la sincronizacion.
// Su unico proposito es hacer la ejecucion observable durante la socializacion del taller,
// permitiendo que el profesor y los evaluadores sigan el flujo paso a paso sin que
// la prueba avance mas rapido de lo que el ojo humano puede seguir.
public class PausaVisible implements Interaction {

    private final int milisegundos;

    public PausaVisible(int milisegundos) {
        this.milisegundos = milisegundos;
    }

    public static PausaVisible deMilisegundos(int ms) {
        return new PausaVisible(ms);
    }

    public static PausaVisible estandar() {
        return new PausaVisible(Constantes.ESPERA_ENTRE_PASOS);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
