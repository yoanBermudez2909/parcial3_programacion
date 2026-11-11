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

    private List<DetallePedido> listDetalles;
    private ServicioDomicilio servicio;

    public Pedido(String codigo, LocalDate fecha, ModalidadEntrega modalidad, EstadoPedido estado, double distancia, ServicioDomicilio servicio) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.modalidad = modalidad;
        this.estado = estado;
        this.distancia = distancia;
        this.servicio = servicio;

        this.listDetalles = new ArrayList<>();

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

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", fecha=" + fecha +
                ", modalidad=" + modalidad +
                ", estado=" + estado +
                ", distancia=" + distancia +
                ", listDetalles=" + listDetalles +
                ", servicio=" + servicio +
                '}';
    }
}