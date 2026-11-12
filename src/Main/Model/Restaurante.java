package Main.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private String nombreComercial;
    private String NIT;
    private String direccion;

    private List<Cliente> listClientes;
    private List<Producto> listProductos;
    private List<Pedido> listPedidos;

    public Restaurante( String direccion, String NIT, String nombreComercial) {
        this.listPedidos = new ArrayList<>();
        this.listProductos = new ArrayList<>();
        this.listClientes = new ArrayList<>();
        this.direccion = direccion;
        this.NIT = NIT;
        this.nombreComercial = nombreComercial;
    }



        public Producto buscarProducto(String codigo) {
            for (Producto p : listProductos) {
                if (p.getCodigo().equals(codigo)) return p;
            }
            return null;
        }


        public Cliente buscarCliente(String documento) {
            for (Cliente c : listClientes) {
                if (c.getDocumento().equals(documento)) return c;
            }
            return null;
        }

        public Pedido buscarPedido(String codigo) {
            for (Pedido p : listPedidos) {
                if (p.getCodigo().equals(codigo)) return p;
            }
            return null;
        }

        public boolean registrarProducto(Producto producto) {
            if (producto == null || buscarProducto(producto.getCodigo()) != null) return false;
            listProductos.add(producto);
            return true;
        }


        public boolean registrarCliente(Cliente cliente) {
            if (cliente == null || buscarCliente(cliente.getDocumento()) != null) return false;
            listClientes.add(cliente);
            return true;
        }


    public boolean registrarPedido(String codigo, LocalDate fecha, String documento, ModalidadEntrega modalidad, Cliente cliente, EstadoPedido estado, double distancia, ServicioDomicilio servicio ) {
        if (fecha == null || modalidad == null) {
            return false;
        }
        if (buscarCliente(documento) == null) {
            return false;
        }
        if (buscarPedido(codigo) != null) {
            return false;
        }
        Pedido pedido = new Pedido(codigo, fecha, modalidad, cliente, estado, distancia, servicio);
        listPedidos.add(pedido);
        return true;
    }

    public List<Pedido> consultarHistorial(String documentoCliente) {
        Cliente cliente = buscarCliente(documentoCliente);
        if(cliente == null){
            return null;
        }
        return cliente.consultarHistorial();
    }


    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public List<Producto> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<Producto> listProductos) {
        this.listProductos = listProductos;
    }

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "nombreComercial='" + nombreComercial + '\'' +
                ", NIT='" + NIT + '\'' +
                ", direccion='" + direccion + '\'' +
                ", listClientes=" + listClientes +
                ", listProductos=" + listProductos +
                ", listPedidos=" + listPedidos +
                '}';
    }
}
