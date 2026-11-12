package Main.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private ModalidadEntrega modalidad;
    private EstadoPedido estado;
    private double distancia;
    private Cliente cliente;
    private ResultadoDomicilio domicilio;

    private List<DetallePedido> listDetalles;
    private ServicioDomicilio servicio;

    public Pedido(String codigo, LocalDate fecha, ModalidadEntrega modalidad, Cliente cliente) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.modalidad = modalidad;
        this.cliente = cliente;
        this.estado = EstadoPedido.PENDIENTE;
        this.distancia = 0;
        this.servicio = null;
        this.domicilio = null;
        this.listDetalles = new ArrayList<>();
    }

    private boolean puedeModificarse() {
        boolean bandera = false;

        if(estado == EstadoPedido.PENDIENTE) {
            bandera = true;
        }

        return bandera;
    }

    public DetallePedido buscarDetalle(String codigoProducto) {
        for (DetallePedido d : listDetalles) {
            if (d.producto().getCodigo().equals(codigoProducto)) {
                return d;
            }
        }
        return null;
    }

    public boolean eliminarDetalle(String codigoProducto) {
        if (puedeModificarse()) {
            DetallePedido detallePedido = buscarDetalle(codigoProducto);

            if (detallePedido != null) {
                listDetalles.remove(detallePedido);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarCantidad(String codigoProducto, int nuevaCantidad) {

        if (!puedeModificarse()) {
            return false;
        }

        if (nuevaCantidad <= 0) {
            return false;
        }

        DetallePedido detalle = buscarDetalle(codigoProducto);

        if (detalle == null) {
            return false;
        }

        if (!detalle.producto().hayExistencias(nuevaCantidad)) {
            return false;
        }

        listDetalles.set(
                listDetalles.indexOf(detalle),
                new DetallePedido(detalle.producto(), nuevaCantidad));

        return true;
    }

    public boolean verificarCalidad() {

        for (DetallePedido det : getListDetalles()) {
            if (!det.producto().cumpleCalidad()) {
                return false;
            }
        }

        return true;
    }

    public boolean agregarDetalle(Producto producto, int cantidad) {
        if (!puedeModificarse()) {
            return false;
        }
        if (producto == null || cantidad <= 0) {
            return false;
        }
        if (!producto.cumpleCalidad()) {
            return false;
        }
        DetallePedido existente = buscarDetalle(producto.getCodigo());
        int total = (existente != null) ? existente.cantidad() + cantidad : cantidad;
        if (!producto.hayExistencias(total)) {
            return false;
        }
        if (existente != null) {
            listDetalles.set(listDetalles.indexOf(existente), new DetallePedido(producto, total));
        } else {
            listDetalles.add(new DetallePedido(producto, cantidad));
        }
        return true;
    }

    public boolean confirmar() {
        if (estado != EstadoPedido.PENDIENTE) {
            return false;
        }
        if (listDetalles.isEmpty()) {
            return false;
        }
        if (!verificarCalidad()) {
            return false;
        }
        for (DetallePedido detalle : listDetalles) {
            if (!detalle.producto().hayExistencias(detalle.cantidad())) {
                return false;
            }
        }
        if (modalidad == ModalidadEntrega.DOMICILIO) {
            if (domicilio == null || !domicilio.aceptado()) {
                return false;
            }
        }
        for (DetallePedido detalle : listDetalles) {
            detalle.producto().descontarExistencias(detalle.cantidad());
        }
        estado = EstadoPedido.CONFIRMADO;
        return true;
    }

    public boolean asignarDomicilio(ServicioDomicilio servicio, double distancia) {
        if (!puedeModificarse()) {
            return false;
        }

        if (modalidad != ModalidadEntrega.DOMICILIO || servicio == null) {
            return false;
        }
        this.servicio = servicio;
        this.distancia = distancia;
        this.domicilio = servicio.solicitarServicio(distancia);

        return domicilio.aceptado();
    }

    public double calcularCostoDomicilio() {

        if (modalidad != ModalidadEntrega.DOMICILIO || domicilio == null) {
            return 0;
        }

        return domicilio.costo();
    }

    public boolean entregar() {

        if (estado != EstadoPedido.CONFIRMADO) {
            return false;
        }

        estado = EstadoPedido.ENTREGADO;
        return true;
    }

    public boolean cancelar() {

        if (estado != EstadoPedido.PENDIENTE) {
            return false;
        }

        estado = EstadoPedido.CANCELADO;
        return true;
    }

    public boolean contabilizaComoIngreso() {
        return estado == EstadoPedido.CONFIRMADO
                || estado == EstadoPedido.ENTREGADO;
    }

    public double calcularTotalProductos() {

        double total = 0;

        for (DetallePedido d : listDetalles) {
            total += d.calcularSubTotal();
        }

        return total;
    }

    public double calcularTotal() {
        return calcularTotalProductos() + calcularCostoDomicilio();
    }

    public String resumen() {
        return "Pedido " + codigo
                + " | Fecha: " + fecha
                + " | Estado: " + estado
                + " | Total: $" + calcularTotal();
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ModalidadEntrega getModalidad() {
        return modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public List<DetallePedido> getListDetalles() {
        return new ArrayList<>(listDetalles);
    }

    public ServicioDomicilio getServicio() {
        return servicio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ResultadoDomicilio getDomicilio() {
        return domicilio;
    }
    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", fecha=" + fecha +
                ", modalidad=" + modalidad +
                ", estado=" + estado +
                ", distancia=" + distancia +
                ", cliente=" + (cliente != null ? cliente.getDocumento() : null) +
                ", listDetalles=" + listDetalles +
                ", servicio=" + servicio +
                '}';
    }
}