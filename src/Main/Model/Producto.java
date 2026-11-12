package Main.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacion;
    private int cantidadProducto;

    private List<DetallePedido> listPedidos;

    public Producto(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, int cantidadProducto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacion = tiempoPreparacion;
        this.cantidadProducto = cantidadProducto;
        this.listPedidos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public List<DetallePedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<DetallePedido> listPedidos) {
        this.listPedidos = listPedidos;
    }
    public abstract double calcularPrecioVenta();
    public abstract boolean cumpleCalidad();
    public abstract CategoriaProducto obtenerCategoria();

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioBase=" + precioBase +
                ", tiempoPreparacion=" + tiempoPreparacion +
                '}';
    }
}