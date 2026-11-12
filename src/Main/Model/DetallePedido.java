package Main.Model;

public record DetallePedido(Producto producto, int cantidad) {

    public double calcularSubTotal() {
        return producto.calcularPrecioVenta() * cantidad;
    }
}



