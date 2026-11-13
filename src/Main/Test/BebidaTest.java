package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    Bebida jugoFrio = new Bebida("B01", "Jugo de mora", "Jugo natural", 6000, 5, 400, 5, true, false, 50);
    Bebida cafeCaliente = new Bebida("B02", "Café especial", "Café de la casa", 5000, 5, 300, 70, false, true, 50);

    @Test
    void calcularPrecioVentaSinPreparacionEspecialAplicaDiezPorCiento() {
        assertEquals(6600, jugoFrio.calcularPrecioVenta(), 0.01);
    }

    @Test
    void calcularPrecioVentaConPreparacionEspecialSumaRecargoFijo() {
        assertEquals(8500, cafeCaliente.calcularPrecioVenta(), 0.01);
    }

    @Test
    void bebidasFriaYCalienteValidasCumplenCalidadYSonCategoriaBebida() {
        assertTrue(jugoFrio.cumpleCalidad());
        assertTrue(cafeCaliente.cumpleCalidad());
        assertEquals(CategoriaProducto.BEBIDA, jugoFrio.obtenerCategoria());
    }

    @Test
    void bebidaConVolumenFueraDeRangoNoCumpleCalidad() {
        Bebida pequena = new Bebida("B03", "Shot", "Shot de jugo", 3000, 5, 100, 5, true, false, 50);
        Bebida enorme = new Bebida("B04", "Jarra", "Jarra de jugo", 9000, 5, 1600, 5, true, false, 50);
        Bebida limiteInferior = new Bebida("B05", "Vaso", "Vaso de jugo", 3000, 5, 150, 5, true, false, 50);
        Bebida limiteSuperior = new Bebida("B06", "Jarra", "Jarra de jugo", 9000, 5, 1500, 5, true, false, 50);

        assertFalse(pequena.cumpleCalidad());
        assertFalse(enorme.cumpleCalidad());
        assertTrue(limiteInferior.cumpleCalidad());
        assertTrue(limiteSuperior.cumpleCalidad());
    }

    @Test
    void bebidaConTemperaturaFueraDelRangoDeSuTipoNoCumpleCalidad() {
        Bebida friaTibia = new Bebida("B07", "Jugo", "Jugo tibio", 6000, 5, 400, 11, true, false, 50);
        Bebida calienteFria = new Bebida("B08", "Café", "Café frío", 5000, 5, 300, 40, false, false, 50);
        Bebida calienteMuyCaliente = new Bebida("B09", "Café", "Café hirviendo", 5000, 5, 300, 86, false, false, 50);
        Bebida friaLimite = new Bebida("B10", "Jugo", "Jugo helado", 6000, 5, 400, 10, true, false, 50);

        assertFalse(friaTibia.cumpleCalidad());
        assertFalse(calienteFria.cumpleCalidad());
        assertFalse(calienteMuyCaliente.cumpleCalidad());
        assertTrue(friaLimite.cumpleCalidad());
    }
}