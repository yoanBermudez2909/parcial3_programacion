package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoFuerteTest {

    PlatoFuerte churrasco = new PlatoFuerte("P01", "Churrasco", "Carne asada", 20000, 30, 400, TipoPreparacion.ASADO, true, 50);

    @Test
    void calcularPrecioVentaAplicaVeintePorCiento() {
        assertEquals(24000, churrasco.calcularPrecioVenta(), 0.01);
    }

    @Test
    void platoValidoCumpleCalidadYEsCategoriaPlatoFuerte() {
        assertTrue(churrasco.cumpleCalidad());
        assertEquals(CategoriaProducto.PLATO_FUERTE, churrasco.obtenerCategoria());
    }

    @Test
    void platoConPesoFueraDeRangoNoCumpleCalidad() {
        PlatoFuerte liviano = new PlatoFuerte("P02", "Porción", "Porción pequeña", 20000, 30, 199, TipoPreparacion.FRITO, true, 50);
        PlatoFuerte pesado = new PlatoFuerte("P03", "Porción", "Porción enorme", 20000, 30, 901, TipoPreparacion.FRITO, true, 50);
        PlatoFuerte limiteInferior = new PlatoFuerte("P04", "Porción", "Porción mínima", 20000, 30, 200, TipoPreparacion.FRITO, true, 50);
        PlatoFuerte limiteSuperior = new PlatoFuerte("P05", "Porción", "Porción máxima", 20000, 30, 900, TipoPreparacion.FRITO, true, 50);

        assertFalse(liviano.cumpleCalidad());
        assertFalse(pesado.cumpleCalidad());
        assertTrue(limiteInferior.cumpleCalidad());
        assertTrue(limiteSuperior.cumpleCalidad());
    }

    @Test
    void platoConTiempoDePreparacionFueraDeRangoNoCumpleCalidad() {
        PlatoFuerte rapido = new PlatoFuerte("P06", "Plato", "Muy rápido", 20000, 9, 400, TipoPreparacion.A_LA_PLANCHA, true, 50);
        PlatoFuerte lento = new PlatoFuerte("P07", "Plato", "Muy lento", 20000, 61, 400, TipoPreparacion.HORNEADO, true, 50);
        PlatoFuerte limiteInferior = new PlatoFuerte("P08", "Plato", "Tiempo mínimo", 20000, 10, 400, TipoPreparacion.HORNEADO, true, 50);
        PlatoFuerte limiteSuperior = new PlatoFuerte("P09", "Plato", "Tiempo máximo", 20000, 60, 400, TipoPreparacion.HORNEADO, true, 50);

        assertFalse(rapido.cumpleCalidad());
        assertFalse(lento.cumpleCalidad());
        assertTrue(limiteInferior.cumpleCalidad());
        assertTrue(limiteSuperior.cumpleCalidad());
    }

    @Test
    void platoConCoccionNoAprobadaNoCumpleCalidad() {
        PlatoFuerte sinAprobar = new PlatoFuerte("P10", "Churrasco", "Cocción pendiente", 20000, 30, 400, TipoPreparacion.ASADO, false, 50);

        assertFalse(sinAprobar.cumpleCalidad());
    }
}