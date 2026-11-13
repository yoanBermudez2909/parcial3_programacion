package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapidoExpressTest {

    RapidoExpress rapido = new RapidoExpress("RapidoExpress", 5000, 1500, 12);

    @Test
    void aceptaRecorridoDentroDelRangoIncluidoElLimite() {
        assertTrue(rapido.aceptaRecorrido(5));
        assertTrue(rapido.aceptaRecorrido(12));
    }

    @Test
    void rechazaRecorridoCeroNegativoOSuperiorAlMaximo() {
        assertFalse(rapido.aceptaRecorrido(0));
        assertFalse(rapido.aceptaRecorrido(-3));
        assertFalse(rapido.aceptaRecorrido(12.1));
        assertFalse(rapido.aceptaRecorrido(15));
    }

    @Test
    void calcularCostoDeUnRecorridoNormal() {
        assertEquals(20000, rapido.calcularCosto(10), 0.01);
    }

    @Test
    void calcularCostoEnElKilometroMaximo() {
        assertEquals(23000, rapido.calcularCosto(12), 0.01);
    }

    @Test
    void calcularCostoDeUnRecorridoNoAceptadoEsCero() {
        assertEquals(0, rapido.calcularCosto(15), 0.01);
        assertEquals(0, rapido.calcularCosto(0), 0.01);
    }
}