package com.bancolombia.certificacion.interactions;

import com.bancolombia.certificacion.utils.Constantes;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.time.Duration;

// Selecciona una fecha de nacimiento usando el calendario propio de Bancolombia.
// Estos campos no aceptan que se escriba la fecha directamente: el banco usa un
// componente de calendario y solo reconoce la fecha cuando el usuario la elige
// haciendo clic. El recorrido natural es abrir el calendario, tocar el encabezado
// para pasar a la vista de anios, retroceder hasta el anio de nacimiento, elegir el
// mes y finalmente el dia. Asi el formulario valida la edad y habilita la simulacion.
// La navegacion se hace por javascript porque el calendario reacciona mejor al clic
// directo sobre cada elemento, sin depender de su posicion ni del tamano de la ventana.
public class SeleccionarFechaCalendario implements Interaction {

    // Abreviaturas de los meses tal como las muestra el calendario del banco.
    private static final String[] MESES = {
        "Ene", "Feb", "Mar", "Abr", "May", "Jun",
        "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };

    private final By campoFecha;
    private final String fecha;

    public SeleccionarFechaCalendario(By campoFecha, String fecha) {
        this.campoFecha = campoFecha;
        this.fecha = fecha;
    }

    // Recibe la fecha en formato dd/MM/aaaa, que es como la lee el negocio.
    public static SeleccionarFechaCalendario en(By campoFecha, String fecha) {
        return new SeleccionarFechaCalendario(campoFecha, fecha);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String[] partes = fecha.split("/");
        String dia = String.valueOf(Integer.parseInt(partes[0]));
        String mes = MESES[Integer.parseInt(partes[1]) - 1];
        String anioObjetivo = partes[2];

        // Serenity entrega su propio envoltorio del navegador, que al hacer clic en un
        // campo de fecha no dispara el calendario del banco. Por eso desenvolvemos hasta
        // el navegador nativo y trabajamos el calendario directamente sobre el.
        WebDriver navegador = obtenerNavegadorNativo(BrowseTheWeb.as(actor).getDriver());
        JavascriptExecutor js = (JavascriptExecutor) navegador;
        WebDriverWait espera = new WebDriverWait(navegador, Duration.ofSeconds(15));
        By encabezadoCalendario = By.cssSelector(".bc-calendar .bc-month");

        // Abrimos el calendario haciendo clic en el campo, llevandolo antes a la vista.
        WebElement campo = navegador.findElement(campoFecha);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", campo);
        pausa(500);

        // El calendario a veces tarda en desplegarse, asi que reintentamos abrirlo
        // hasta confirmar que su encabezado ya esta presente en pantalla.
        boolean calendarioAbierto = false;
        for (int intento = 0; intento < 3 && !calendarioAbierto; intento++) {
            abrirCalendario(navegador, campo, intento);
            try {
                espera.until(ExpectedConditions.presenceOfElementLocated(encabezadoCalendario));
                calendarioAbierto = true;
            } catch (RuntimeException reintentar) {
                pausa(1000);
            }
        }

        if (!calendarioAbierto) {
            throw new IllegalStateException(diagnostico(navegador, campo));
        }

        // Pasamos a la vista de meses tocando el encabezado. Desde alli las flechas
        // del calendario navegan por anios, lo que evita cientos de clics mes a mes.
        clicEnEncabezado(js);
        pausa(800);

        // Retrocedemos o avanzamos los anios hasta llegar al anio de nacimiento.
        for (int intentos = 0; intentos < 200; intentos++) {
            String anioActual = anioMostrado(js);
            if (anioActual.equals(anioObjetivo)) {
                break;
            }
            boolean retroceder = Integer.parseInt(anioActual) > Integer.parseInt(anioObjetivo);
            clicEnFlecha(js, retroceder);
            pausa(300);
        }

        // Elegimos el mes y luego el dia, ignorando los dias que el banco deshabilita.
        clicEnTextoDelCalendario(js, mes);
        pausa(800);
        clicEnDia(js, dia);
        pausa(Constantes.ESPERA_ENTRE_PASOS);
    }

    // Hace clic en el encabezado del calendario para alternar a la vista de meses/anios.
    private void clicEnEncabezado(JavascriptExecutor js) {
        js.executeScript("var h=document.querySelector('.bc-calendar .bc-month'); if(h){h.click();}");
    }

    // Lee el anio que muestra el encabezado del calendario. En la vista de meses el
    // anio queda en un encabezado distinto al de la vista de dias, por eso buscamos
    // cualquier encabezado h6 del calendario, que sirve para ambas vistas.
    private String anioMostrado(JavascriptExecutor js) {
        Object valor = js.executeScript(
            "var h=document.querySelector('.bc-calendar h6');"
          + "return h ? h.innerText.replace(/[^0-9]/g,'') : '';");
        return valor == null ? "" : valor.toString();
    }

    // Hace clic en la flecha de retroceso o avance de anio del calendario.
    private void clicEnFlecha(JavascriptExecutor js, boolean retroceder) {
        String clase = retroceder ? "prev" : "next";
        js.executeScript(
            "var f=document.querySelector('.bc-calendar ." + clase + "'); if(f){f.click();}");
    }

    // Busca dentro del calendario el elemento cuyo texto coincide con el valor buscado
    // (por ejemplo la abreviatura de un mes) y le hace clic.
    private void clicEnTextoDelCalendario(JavascriptExecutor js, String texto) {
        js.executeScript(
            "var t=arguments[0];"
          + "var nodos=document.querySelectorAll('.bc-calendar p, .bc-calendar span, .bc-calendar div');"
          + "for(var i=0;i<nodos.length;i++){"
          + "  if(nodos[i].innerText && nodos[i].innerText.trim()===t && nodos[i].offsetParent!==null){"
          + "    nodos[i].click(); return;"
          + "  }"
          + "}", texto);
    }

    // Hace clic en el dia indicado dentro de la grilla del calendario, evitando
    // los dias deshabilitados que el banco no permite seleccionar.
    private void clicEnDia(JavascriptExecutor js, String dia) {
        js.executeScript(
            "var d=arguments[0];"
          + "var dias=document.querySelectorAll('.bc-calendar-days p.day');"
          + "for(var i=0;i<dias.length;i++){"
          + "  if(dias[i].innerText && dias[i].innerText.trim()===d"
          + "     && dias[i].className.indexOf('disabled')===-1){"
          + "    dias[i].click(); return;"
          + "  }"
          + "}", dia);
    }

    // Reune informacion del estado real de la pagina cuando el calendario no abre,
    // para entender por que el componente del banco no respondio al clic.
    private String diagnostico(WebDriver navegador, WebElement campo) {
        Object info = ((JavascriptExecutor) navegador).executeScript(
            "var c=arguments[0];"
          + "var cals=document.querySelectorAll('.bc-calendar').length;"
          + "var r=c.getBoundingClientRect();"
          + "var top=document.elementFromPoint(r.left+r.width/2, r.top+r.height/2);"
          + "return 'driver='+arguments[1]"
          + "+' | campoTipo='+c.getAttribute('type')+' valor='+c.value"
          + "+' | bcCalendar='+cals"
          + "+' | tapaClick='+(top?top.tagName+'#'+(top.id||''):'?');",
            campo, navegador.getClass().getName());
        return "El calendario no se desplego. Estado: " + info;
    }

    // Desenvuelve el navegador hasta llegar al driver nativo de Chrome.
    // Serenity envuelve el WebDriver en varias capas y esas capas modifican el
    // comportamiento del clic, por eso necesitamos el navegador original para
    // que el calendario del banco responda como lo haria un usuario real.
    private WebDriver obtenerNavegadorNativo(WebDriver navegador) {
        WebDriver actual = navegador;

        // La capa principal de Serenity expone el navegador real con getProxiedDriver.
        try {
            Method metodo = actual.getClass().getMethod("getProxiedDriver");
            Object interno = metodo.invoke(actual);
            if (interno instanceof WebDriver) {
                actual = (WebDriver) interno;
            }
        } catch (ReflectiveOperationException sinMetodo) {
            // Si esta capa no expone getProxiedDriver seguimos con las demas.
        }

        // Por si quedan envoltorios adicionales, los retiramos uno a uno.
        while (actual instanceof WrapsDriver) {
            WebDriver interno = ((WrapsDriver) actual).getWrappedDriver();
            if (interno == null || interno == actual) {
                break;
            }
            actual = interno;
        }
        return actual;
    }

    // Intenta desplegar el calendario sobre el campo de fecha. Como el componente
    // del banco no siempre reacciona igual, vamos cambiando la forma de hacer clic
    // en cada reintento: primero el clic normal y luego enfocando y forzando por javascript.
    private void abrirCalendario(WebDriver navegador, WebElement campo, int intento) {
        if (intento == 0) {
            campo.click();
        } else {
            ((JavascriptExecutor) navegador).executeScript(
                "arguments[0].focus(); arguments[0].click();", campo);
        }
    }

    // Pausa breve para que el calendario alcance a redibujarse entre cada accion.
    private void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
