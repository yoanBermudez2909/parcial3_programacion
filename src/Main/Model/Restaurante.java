package Main.Model;

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
