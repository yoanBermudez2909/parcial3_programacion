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


    public Producto(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, int cantidadProducto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacion = tiempoPreparacion;
        this.cantidadProducto = cantidadProducto;
    }

    public boolean datosValidos() {
        return codigo != null && !codigo.isBlank()
                && nombre != null && !nombre.isBlank()
                && precioBase > 0
                && tiempoPreparacion > 0
                && cantidadProducto >= 0;
    }

    public abstract double calcularPrecioVenta();


    public abstract boolean cumpleCalidad();


    public abstract CategoriaProducto obtenerCategoria();

    public boolean descontarExistencias(int cantidad) {
        if (!hayExistencias(cantidad)) return false;
        cantidadProducto -= cantidad;
        return true;
    }

    public boolean actualizarExistencias(int nuevasExistencias) {
        if (nuevasExistencias < 0) return false;
        cantidadProducto = nuevasExistencias;
        return true;
    }

    public boolean hayExistencias(int cantidad) {
        return cantidad > 0 && cantidadProducto >= cantidad;
    }


    public String getCodigo() {
        return codigo;
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
    public int getCantidadProducto() { return cantidadProducto; }


    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioBase=" + precioBase +
                ", tiempoPreparacion=" + tiempoPreparacion +
                ", cantidadProducto=" + cantidadProducto +
                '}';
    }
}