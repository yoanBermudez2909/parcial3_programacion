package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaSeguraTest {

    EntregaSegura entrega = new EntregaSegura("EntregaSegura", 7000, 1000, 3000, 5, 20);

    @Test
    void aceptaRecorridoDentroDelRangoIncluidoElLimite() {
        assertTrue(entrega.aceptaRecorrido(8));
        assertTrue(entrega.aceptaRecorrido(20));
    }

    @Test
    void rechazaRecorridoInvalidoYSuCostoEsCero() {
        assertFalse(entrega.aceptaRecorrido(0));
        assertFalse(entrega.aceptaRecorrido(-2));
        assertFalse(entrega.aceptaRecorrido(20.5));
        assertEquals(0, entrega.calcularCosto(25), 0.01);
    }

    @Test
    void calcularCostoSinRecargoCuandoNoSuperaElUmbral() {
        assertEquals(11000, entrega.calcularCosto(4), 0.01);
    }

    @Test
    void calcularCostoEnElUmbralExactoNoAplicaRecargo() {
        assertEquals(12000, entrega.calcularCosto(5), 0.01);
    }

    @Test
    void calcularCostoConRecargoCuandoSuperaElUmbral() {
        assertEquals(20000, entrega.calcularCosto(10), 0.01);
        assertEquals(30000, entrega.calcularCosto(20), 0.01);
    }
}