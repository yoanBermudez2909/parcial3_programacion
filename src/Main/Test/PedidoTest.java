package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    Cliente cliente1 = new Cliente("1001", "Ana Gómez", "3001234567", "ana@gmail.com", "Cra 14 #10-05");


    Bebida jugo = new Bebida("B01", "Jugo de mora", "Jugo natural", 6000, 5, 400, 5, true, false, 50);
    RapidoExpress rapido = new RapidoExpress("RapidoExpress", 5000, 1500, 12);


    @Test
    void agregarDetalleRechazaCantidadInvalidaYProductoNulo() {
        Pedido pedido = new Pedido("P1", LocalDate.now(), ModalidadEntrega.CONSUMO_RESTAURANTE, cliente1);

        assertFalse(pedido.agregarDetalle(jugo, 0));
        assertFalse(pedido.agregarDetalle(jugo, -3));
        assertFalse(pedido.agregarDetalle(null, 1));
        assertTrue(pedido.getListDetalles().isEmpty());

        assertTrue(pedido.agregarDetalle(jugo, 1));
        assertEquals(1, pedido.getListDetalles().size());
    }

    @Test
    void agregarMismoProductoAcumulaCantidadYRespetaExistencias() {
        Pedido pedido = new Pedido("P2", LocalDate.now(), ModalidadEntrega.CONSUMO_RESTAURANTE, cliente1);

        assertTrue(pedido.agregarDetalle(jugo, 3));
        assertTrue(pedido.agregarDetalle(jugo, 2));
        assertEquals(1, pedido.getListDetalles().size());
        assertEquals(5, pedido.buscarDetalle("B01").cantidad());

        assertFalse(pedido.agregarDetalle(jugo, 46));
        assertEquals(5, pedido.buscarDetalle("B01").cantidad());
    }

    @Test
    void calcularTotalIncluyeElCostoDelDomicilioSoloSiCorresponde() {
        Pedido enLocal = new Pedido("P3", LocalDate.now(), ModalidadEntrega.CONSUMO_RESTAURANTE, cliente1);
        enLocal.agregarDetalle(jugo, 2);

        Pedido aDomicilio = new Pedido("P4", LocalDate.now(), ModalidadEntrega.DOMICILIO, cliente1);
        aDomicilio.agregarDetalle(jugo, 2);
        aDomicilio.asignarDomicilio(rapido, 10);

        assertEquals(13200, enLocal.calcularTotal(), 0.01);
        assertEquals(0, enLocal.calcularCostoDomicilio(), 0.01);
        assertEquals(33200, aDomicilio.calcularTotal(), 0.01);
    }

    @Test
    void pedidoADomicilioSoloSeConfirmaConServicioAceptadoYLuegoSeEntrega() {
        Pedido pedido = new Pedido("P5", LocalDate.now(), ModalidadEntrega.DOMICILIO, cliente1);
        pedido.agregarDetalle(jugo, 2);

        assertFalse(pedido.confirmar());
        assertFalse(pedido.asignarDomicilio(rapido, 15));
        assertFalse(pedido.confirmar());
        assertTrue(pedido.asignarDomicilio(rapido, 10));
        assertTrue(pedido.confirmar());

        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
        assertTrue(jugo.hayExistencias(48));
        assertFalse(jugo.hayExistencias(49));
        assertTrue(pedido.contabilizaComoIngreso());

        assertFalse(pedido.cancelar());
        assertTrue(pedido.entregar());
        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
    }

    @Test
    void pedidoCanceladoNoSeEntregaNiSeConfirmaNiSeContabiliza() {
        Pedido pedido = new Pedido("P6", LocalDate.now(), ModalidadEntrega.CONSUMO_RESTAURANTE, cliente1);
        pedido.agregarDetalle(jugo, 1);

        assertTrue(pedido.cancelar());
        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        assertFalse(pedido.entregar());
        assertFalse(pedido.confirmar());
        assertFalse(pedido.agregarDetalle(jugo, 1));
        assertFalse(pedido.contabilizaComoIngreso());
        assertTrue(jugo.hayExistencias(50));
    }
}