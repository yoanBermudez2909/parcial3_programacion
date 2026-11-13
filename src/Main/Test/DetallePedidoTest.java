package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    Bebida jugo = new Bebida("B01", "Jugo de mora", "Jugo natural", 6000, 5, 400, 5, true, false, 40);
    Bebida cafe = new Bebida("B02", "Café especial", "Café de la casa", 5000, 5, 300, 70, false, true, 50);

    @Test
    void calcularSubTotalConUnaUnidad() {
        DetallePedido detalle = new DetallePedido(jugo, 1);
        assertEquals(6600, detalle.calcularSubTotal(), 0.01);
    }

    @Test
    void calcularSubTotalConVariasUnidades() {
        DetallePedido detalle = new DetallePedido(jugo, 3);
        assertEquals(19800, detalle.calcularSubTotal(), 0.01);
    }

    @Test
    void calcularSubTotalConProductoConRecargoEspecial() {
        DetallePedido detalle = new DetallePedido(cafe, 2);
        assertEquals(17000, detalle.calcularSubTotal(), 0.01);
    }

    @Test
    void elDetalleConservaElProductoYLaCantidad() {
        DetallePedido detalle = new DetallePedido(jugo, 4);
        assertSame(jugo, detalle.producto());
        assertEquals(4, detalle.cantidad());
    }

    @Test
    void detallesConLosMismosDatosSonIgualesYConDistintaCantidadNo() {
        DetallePedido a = new DetallePedido(jugo, 2);
        DetallePedido b = new DetallePedido(jugo, 2);
        DetallePedido c = new DetallePedido(jugo, 5);
        assertEquals(a, b);
        assertNotEquals(a, c);
    }
}