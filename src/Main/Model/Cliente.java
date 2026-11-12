package Main.Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion;

    private List<Pedido> listPedidosCliente;

    /**
     * Metodo constructor de la clase Cliente
     * @param documento del cliente
     * @param nombreCompleto del cliente
     * @param telefono del cliente
     * @param correo del cliente
     * @param direccion del cliente
     */
    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;

        this.listPedidosCliente = new ArrayList<>();
    }

    /**
     * Metodo que permite verificar si el cliente contiene un pedido
     * @param codigo del pedido a verificar
     * @return true si el pedido se encuentra registrado, false en caso contrario
     */
    public boolean contienePedido(String codigo) {
        for (Pedido p : listPedidosCliente) {
            if (p.getCodigo().equals(codigo)) return true;
        }
        return false;
    }

    /**
     * Metodo que permite agregar un pedido al historial del cliente
     * @param pedido que se desea agregar
     * @return true si el pedido fue agregado correctamente, false en caso contrario
     */
    public boolean agregarPedido(Pedido pedido) {
        if (pedido == null) return false;
        if (pedido.getCliente() != this) return false;
        if (contienePedido(pedido.getCodigo())) return false;
        if (pedido.getEstado() != EstadoPedido.CONFIRMADO
                && pedido.getEstado() != EstadoPedido.ENTREGADO) return false;
        listPedidosCliente.add(pedido);
        return true;
    }

    /**
     * Metodo que permite consultar el historial de pedidos del cliente
     * @return lista con los pedidos registrados en el historial del cliente
     */
    public List<Pedido> consultarHistorial() {
        return new ArrayList<>(listPedidosCliente);
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Pedido> getListPedidosCliente() {
        return new ArrayList<>(listPedidosCliente);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "documento='" + documento + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", listPedidosCliente=" + listPedidosCliente +
                '}';
    }
}