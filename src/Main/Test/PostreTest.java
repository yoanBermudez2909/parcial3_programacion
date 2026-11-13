package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostreTest {

    Postre flan = new Postre("D01", "Flan de caramelo", "Flan casero", 8000, 10, 150, 4, true, 50);
    Postre brownie = new Postre("D02", "Brownie", "Brownie de chocolate", 6000, 15, 120, 22, false, 50);

    @Test
    void calcularPrecioVentaSinRefrigeracionAplicaQuincePorCiento() {
        assertEquals(6900, brownie.calcularPrecioVenta(), 0.01);
    }

    @Test
    void calcularPrecioVentaConRefrigeracionSumaCincoPorCientoAdicional() {
        assertEquals(9600, flan.calcularPrecioVenta(), 0.01);
    }

    @Test
    void postresValidosCumplenCalidadYSonCategoriaPostre() {
        assertTrue(flan.cumpleCalidad());
        assertTrue(brownie.cumpleCalidad());
        assertEquals(CategoriaProducto.POSTRE, flan.obtenerCategoria());
    }

    @Test
    void postreConPesoFueraDeRangoNoCumpleCalidad() {
        Postre liviano = new Postre("D03", "Bocado", "Muy pequeño", 3000, 10, 79, 4, true, 50);
        Postre pesado = new Postre("D04", "Torta", "Muy grande", 15000, 10, 501, 4, true, 50);
        Postre limiteInferior = new Postre("D05", "Bocado", "Peso mínimo", 3000, 10, 80, 4, true, 50);
        Postre limiteSuperior = new Postre("D06", "Torta", "Peso máximo", 15000, 10, 500, 4, true, 50);

        assertFalse(liviano.cumpleCalidad());
        assertFalse(pesado.cumpleCalidad());
        assertTrue(limiteInferior.cumpleCalidad());
        assertTrue(limiteSuperior.cumpleCalidad());
    }

    @Test
    void postreConTemperaturaFueraDelRangoSegunRefrigeracionNoCumpleCalidad() {
        Postre refrigeradoTibio = new Postre("D07", "Flan", "Refrigerado a 7", 8000, 10, 150, 7, true, 50);
        Postre refrigeradoAmbiente = new Postre("D08", "Flan", "Refrigerado a 20", 8000, 10, 150, 20, true, 50);
        Postre ambienteFrio = new Postre("D09", "Brownie", "Ambiente a 10", 6000, 15, 120, 10, false, 50);
        Postre ambienteCaliente = new Postre("D10", "Brownie", "Ambiente a 31", 6000, 15, 120, 31, false, 50);
        Postre refrigeradoLimite = new Postre("D11", "Flan", "Refrigerado a 6", 8000, 10, 150, 6, true, 50);

        assertFalse(refrigeradoTibio.cumpleCalidad());
        assertFalse(refrigeradoAmbiente.cumpleCalidad());
        assertFalse(ambienteFrio.cumpleCalidad());
        assertFalse(ambienteCaliente.cumpleCalidad());
        assertTrue(refrigeradoLimite.cumpleCalidad());
    }
}