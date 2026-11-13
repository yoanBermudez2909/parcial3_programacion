package Main.Test;

import Main.Model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class RestauranteTest {

    Restaurante restaurante = new Restaurante("Calle 15 #14-20", "900123456-1", "El Buen Comer");


    Cliente cliente1 = new Cliente("1001", "Ana Gómez", "3001234567", "ana@gmail.com", "Cra 14 #10-05");
    Bebida jugo = new Bebida("B01", "Jugo de mora", "Jugo natural", 6000, 5, 400, 5, true, false, 2);
    Bebida limonada = new Bebida("B01", "Limonada", "Limonada natural", 5000, 5, 350, 4, true, false, 3);

    @Test
    void registrarProductoConCodigoDuplicadoRetornaFalse() {
        assertTrue(restaurante.registrarProducto(jugo));
        assertFalse(restaurante.registrarProducto(limonada));
        assertSame(jugo, restaurante.buscarProducto("B01"));
    }

    @Test
    void registrarClienteConDocumentoDuplicadoRetornaFalse() {
        Cliente repetido = new Cliente("1001", "Otra Persona", "3109998888", "otra@gmail.com", "Calle 1 #2-3");

        assertTrue(restaurante.registrarCliente(cliente1));
        assertFalse(restaurante.registrarCliente(repetido)); // mismo documento "1001"
        assertSame(cliente1, restaurante.buscarCliente("1001"));
    }

    @Test
    void registrarPedidoExigeClienteRegistradoYCodigoUnico() {

        assertFalse(restaurante.registrarPedido("P1", LocalDate.now(), "1001", ModalidadEntrega.CONSUMO_RESTAURANTE));

        restaurante.registrarCliente(cliente1);

        assertTrue(restaurante.registrarPedido("P1", LocalDate.now(), "1001", ModalidadEntrega.CONSUMO_RESTAURANTE));
        assertNotNull(restaurante.buscarPedido("P1"));

        assertFalse(restaurante.registrarPedido("P1", LocalDate.now(), "1001", ModalidadEntrega.CONSUMO_RESTAURANTE));
    }

    @Test
    void consultarHistorialDeClienteInexistenteRetornaNullYPedidoPendienteNoApareceEnHistorial() {
        assertNull(restaurante.consultarHistorial("9999"));

        restaurante.registrarCliente(cliente1);
        restaurante.registrarPedido("P1", LocalDate.now(), "1001", ModalidadEntrega.CONSUMO_RESTAURANTE);

        List<Pedido> historial = restaurante.consultarHistorial("1001");
        assertNotNull(historial);
        assertTrue(historial.isEmpty());
    }

    @Test
    void confirmarPedidoInexistenteOSinDetallesRetornaFalse() {
        assertFalse(restaurante.confirmarPedido("NO-EXISTE"));

        restaurante.registrarCliente(cliente1);
        restaurante.registrarPedido("P1", LocalDate.now(), "1001", ModalidadEntrega.CONSUMO_RESTAURANTE);

        assertFalse(restaurante.confirmarPedido("P1")); // sin detalles
        assertEquals(EstadoPedido.PENDIENTE, restaurante.buscarPedido("P1").getEstado());
        assertTrue(cliente1.consultarHistorial().isEmpty());
    }
}