package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    Cliente cliente1 = new Cliente("1001", "Ana Gómez", "3001234567", "ana@gmail.com", "Cra 14 #10-05");
    Cliente cliente2 = new Cliente("2002", "Luis Pérez", "3109998888", "luis@gmail.com", "Calle 1 #2-3");


    Bebida jugo = new Bebida("B01", "Jugo de mora", "Jugo natural", 6000, 5, 400, 5, true, false, 50);

    private Pedido crearPedidoConfirmado(String codigo, Cliente cliente) {
        Pedido pedido = new Pedido(codigo, LocalDate.now(), ModalidadEntrega.CONSUMO_RESTAURANTE, cliente);
        pedido.agregarDetalle(jugo, 1);
        pedido.confirmar();
        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
        return pedido;
    }

    @Test
    void clienteNuevoTieneHistorialVacio() {
        assertNotNull(cliente1.consultarHistorial());
        assertTrue(cliente1.consultarHistorial().isEmpty());
        assertFalse(cliente1.contienePedido("P1"));
    }

    @Test
    void agregarPedidoConfirmadoLoRegistraEnElHistorial() {
        Pedido pedido = crearPedidoConfirmado("P1", cliente1);

        assertTrue(cliente1.agregarPedido(pedido));
        assertTrue(cliente1.contienePedido("P1"));
        assertEquals(1, cliente1.consultarHistorial().size());
    }

    @Test
    void agregarPedidoConCodigoRepetidoRetornaFalse() {
        Pedido pedido = crearPedidoConfirmado("P1", cliente1);

        assertTrue(cliente1.agregarPedido(pedido));
        assertFalse(cliente1.agregarPedido(pedido));
        assertEquals(1, cliente1.consultarHistorial().size());
    }

    @Test
    void agregarPedidoNuloPendienteOAjenoRetornaFalse() {
        Pedido pendiente = new Pedido("P2", LocalDate.now(), ModalidadEntrega.CONSUMO_RESTAURANTE, cliente1);
        Pedido deOtroCliente = crearPedidoConfirmado("P3", cliente2);

        assertFalse(cliente1.agregarPedido(null));
        assertFalse(cliente1.agregarPedido(pendiente));
        assertFalse(cliente1.agregarPedido(deOtroCliente));
        assertTrue(cliente1.consultarHistorial().isEmpty());
    }

    @Test
    void consultarHistorialRetornaUnaCopiaProtegida() {
        cliente1.agregarPedido(crearPedidoConfirmado("P1", cliente1));

        List<Pedido> copia = cliente1.consultarHistorial();
        copia.clear();

        assertEquals(1, cliente1.consultarHistorial().size());
    }
}