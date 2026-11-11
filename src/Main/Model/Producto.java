package Main.Model;

import java.util.ArrayList;
import java.util.List;

public class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacion;

    private List<DetallePedido> listPedidos;

    public Producto(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacion = tiempoPreparacion;
        this.listPedidos = new ArrayList<>();
    }
}