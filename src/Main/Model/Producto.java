
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

    /**
     * Crea un producto con sus datos generales y existencias iniciales.
     *
     * @param codigo Código identificador del producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precioBase Precio base del producto.
     * @param tiempoPreparacion Tiempo necesario para preparar el producto.
     * @param cantidadProducto Cantidad inicial disponible del producto.
     */
    public Producto(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, int cantidadProducto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacion = tiempoPreparacion;
        this.cantidadProducto = cantidadProducto;
    }

    /**
     * Verifica que los datos generales del producto sean válidos.
     * Comprueba que el código y el nombre no estén vacíos, que el precio
     * y el tiempo de preparación sean mayores que cero y que las existencias
     * no sean negativas.
     *
     * @return true si todos los datos cumplen las condiciones establecidas; false en caso contrario.
     */
    public boolean datosValidos() {
        return codigo != null && !codigo.isBlank()
                && nombre != null && !nombre.isBlank()
                && precioBase > 0
                && tiempoPreparacion > 0
                && cantidadProducto >= 0;
    }

    /**
     * Calcula el precio de venta del producto según su tipo específico.
     *
     * @return Precio de venta calculado.
     */
    public abstract double calcularPrecioVenta();

    /**
     * Verifica si el producto cumple las condiciones de calidad establecidas
     * para su tipo específico.
     *
     * @return true si cumple las condiciones de calidad; false en caso contrario.
     */
    public abstract boolean cumpleCalidad();

    /**
     * Obtiene la categoría correspondiente al tipo específico de producto.
     *
     * @return Categoría del producto.
     */
    public abstract CategoriaProducto obtenerCategoria();

    /**
     * Descuenta una cantidad determinada de las existencias del producto,
     * siempre que haya unidades suficientes disponibles.
     *
     * @param cantidad Cantidad de unidades que se desea descontar.
     * @return true si el descuento se realiza correctamente; false si no hay existencias suficientes o la cantidad no es válida.
     */
    public boolean descontarExistencias(int cantidad) {
        if (!hayExistencias(cantidad)) return false;
        cantidadProducto -= cantidad;
        return true;
    }

    /**
     * Actualiza la cantidad disponible del producto, siempre que el nuevo
     * valor no sea negativo.
     *
     * @param nuevasExistencias Nueva cantidad de unidades disponibles.
     * @return true si las existencias se actualizan correctamente; false si el valor es negativo.
     */
    public boolean actualizarExistencias(int nuevasExistencias) {
        if (nuevasExistencias < 0) return false;
        cantidadProducto = nuevasExistencias;
        return true;
    }

    /**
     * Comprueba si existen suficientes unidades del producto para satisfacer
     * una cantidad solicitada.
     *
     * @param cantidad Cantidad de unidades requeridas.
     * @return true si la cantidad solicitada es positiva y hay existencias suficientes; false en caso contrario.
     */
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
