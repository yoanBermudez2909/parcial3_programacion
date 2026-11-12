package Main.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido{

    private String codigo;
    private LocalDate fecha;
    private ModalidadEntrega modalidad;
    private EstadoPedido estado;
    private double distancia;
    private  Cliente cliente;

    private List<DetallePedido> listDetalles;
    private ServicioDomicilio servicio;

    public Pedido(String codigo, LocalDate fecha, ModalidadEntrega modalidad, Cliente cliente, EstadoPedido estado, double distancia, ServicioDomicilio servicio) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.modalidad = modalidad;
        this.cliente = cliente;
        this.estado = estado;
        this.distancia = distancia;
        this.servicio = servicio;

        this.listDetalles = new ArrayList<>();

    }

            private boolean puedeModificarse(){
            boolean bandera = false;
            if(estado == EstadoPedido.PENDIENTE){
                bandera = true;

            }
        return bandera;
    }

    public DetallePedido buscarDetalle(String codigoProducto) {
        for (DetallePedido d : listDetalles) {
            if (d.producto().getCodigo().equals(codigoProducto)) return d;
        }
        return null;
    }



    public boolean eliminarDetalle(String codigoProducto) {
        if(puedeModificarse() == true){
            DetallePedido detallePedido = buscarDetalle(codigoProducto);
            if (detallePedido != null){
                listDetalles.remove(detallePedido);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarCantidad(String codigoProducto, int nuevaCantidad) {
        if(!puedeModificarse()) {
            return false;
        }
        if(nuevaCantidad <= 0) {
            return false;
        }
        DetallePedido detalle = buscarDetalle(codigoProducto);
        if(detalle == null) {
            return false;
        }
        if(!detalle.producto().hayExistencias(nuevaCantidad)) {
            return false;
        }
        listDetalles.set(
                listDetalles.indexOf(detalle),
                new DetallePedido(detalle.producto(), nuevaCantidad, cliente));
        return true;
    }

    // [43/60] Pedido.verificarCalidad() — DEPENDIENTE — EN DIAGRAMA
    // DATOS: detalles
    // OBJETIVO: true si TODOS los productos del pedido cumplen calidad.
    // PISTAS: recorre detalles; si alguno tiene !producto().cumpleCalidad(), retorna false. Un pedido vacío devuelve true
    //         (la regla "sin detalles no se confirma" se valida en [46]).
    // ◄ USA: Producto.cumpleCalidad ([07]/[10]/[13])
    public boolean verificarCalidad() {
        for(DetallePedido det:getListDetalles()){
            if(!det.producto().cumpleCalidad()){
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