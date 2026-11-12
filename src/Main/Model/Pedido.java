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

    public Pedido(String codigo, LocalDate fecha, ModalidadEntrega modalidad,
                  Cliente cliente, EstadoPedido estado,
                  double distancia, ServicioDomicilio servicio) {

        this.codigo = codigo;
        this.fecha = fecha;
        this.modalidad = modalidad;
        this.cliente = cliente;
        this.estado = estado;
        this.distancia = distancia;
        this.servicio = servicio;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public void setModalidad(ModalidadEntrega modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public List<DetallePedido> getListDetalles() {
        return listDetalles;
    }

    public void setListDetalles(List<DetallePedido> listDetalles) {
        this.listDetalles = listDetalles;
    }

    public ServicioDomicilio getServicio() {
        return servicio;
    }

    public void setServicio(ServicioDomicilio servicio) {
        this.servicio = servicio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ResultadoDomicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(ResultadoDomicilio domicilio) {
        this.domicilio = domicilio;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", fecha=" + fecha +
                ", modalidad=" + modalidad +
                ", estado=" + estado +
                ", distancia=" + distancia +
                ", cliente=" + cliente +
                ", listDetalles=" + listDetalles +
                ", servicio=" + servicio +
                '}';
    }
}