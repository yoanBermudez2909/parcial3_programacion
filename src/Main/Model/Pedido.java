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

    /**
     * Metodo constructor de la clase Pedido
     * @param codigo del pedido
     * @param fecha de realizacion del pedido
     * @param modalidad de entrega del pedido
     * @param cliente que realiza el pedido
     */
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

    /**
     * Metodo que permite verificar si el pedido puede modificarse
     * @return true si el pedido se encuentra pendiente, false en caso contrario
     */
    private boolean puedeModificarse() {
        boolean bandera = false;

        if(estado == EstadoPedido.PENDIENTE) {
            bandera = true;
        }

        return bandera;
    }

    /**
     * Metodo que permite buscar un detalle dentro del pedido
     * @param codigoProducto del producto a buscar
     * @return detalle del pedido encontrado o null si no existe
     */
    public DetallePedido buscarDetalle(String codigoProducto) {
        for (DetallePedido d : listDetalles) {
            if (d.producto().getCodigo().equals(codigoProducto)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Metodo que permite eliminar un detalle del pedido
     * @param codigoProducto del producto que se desea eliminar
     * @return true si el detalle fue eliminado correctamente, false en caso contrario
     */
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

    /**
     * Metodo que permite actualizar la cantidad de un producto dentro del pedido
     * @param codigoProducto del producto a actualizar
     * @param nuevaCantidad que se desea establecer
     * @return true si la cantidad fue actualizada correctamente, false en caso contrario
     */
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

    /**
     * Metodo que permite verificar si los productos del pedido cumplen con los parametros de calidad
     * @return true si todos los productos cumplen con los parametros de calidad, false en caso contrario
     */
    public boolean verificarCalidad() {

        for (DetallePedido det : getListDetalles()) {
            if (!det.producto().cumpleCalidad()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Metodo que permite agregar un producto al pedido
     * @param producto que se desea agregar
     * @param cantidad del producto que se desea agregar
     * @return true si el producto fue agregado correctamente, false en caso contrario
     */
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

    /**
     * Metodo que permite confirmar el pedido
     * @return true si el pedido fue confirmado correctamente, false en caso contrario
     */
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

    /**
     * Metodo que permite asignar un servicio de domicilio al pedido
     * @param servicio de domicilio que se desea asignar
     * @param distancia del recorrido en kilometros
     * @return true si el servicio fue aceptado, false en caso contrario
     */
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

    /**
     * Metodo que permite calcular el costo del servicio de domicilio
     * @return costo del domicilio o 0 si no corresponde a esta modalidad
     */
    public double calcularCostoDomicilio() {

        if (modalidad != ModalidadEntrega.DOMICILIO || domicilio == null) {
            return 0;
        }

        return domicilio.costo();
    }

    /**
     * Metodo que permite cambiar el estado del pedido a entregado
     * @return true si el pedido fue entregado correctamente, false en caso contrario
     */
    public boolean entregar() {

        if (estado != EstadoPedido.CONFIRMADO) {
            return false;
        }

        estado = EstadoPedido.ENTREGADO;
        return true;
    }

    /**
     * Metodo que permite cancelar el pedido
     * @return true si el pedido fue cancelado correctamente, false en caso contrario
     */
    public boolean cancelar() {

        if (estado != EstadoPedido.PENDIENTE) {
            return false;
        }

        estado = EstadoPedido.CANCELADO;
        return true;
    }

    /**
     * Metodo que permite verificar si el pedido debe contabilizarse como ingreso
     * @return true si el pedido esta confirmado o entregado, false en caso contrario
     */
    public boolean contabilizaComoIngreso() {
        return estado == EstadoPedido.CONFIRMADO
                || estado == EstadoPedido.ENTREGADO;
    }

    /**
     * Metodo que permite calcular el total de los productos del pedido
     * @return suma de los subtotales de los productos
     */
    public double calcularTotalProductos() {

        double total = 0;

        for (DetallePedido d : listDetalles) {
            total += d.calcularSubTotal();
        }

        return total;
    }

    /**
     * Metodo que permite calcular el valor total del pedido
     * @return total de los productos mas el costo del domicilio
     */
    public double calcularTotal() {
        return calcularTotalProductos() + calcularCostoDomicilio();
    }

    /**
     * Metodo que permite generar un resumen del pedido
     * @return resumen con el codigo, fecha, estado y total del pedido
     */
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