package Main.Model;

public record DetallePedido(Producto producto, int cantidad) {

    /**
     * Metodo que permite calcular el subtotal del detalle del pedido
     * @return subtotal del producto multiplicado por la cantidad solicitada
     */
    public double calcularSubTotal() {
        return producto.calcularPrecioVenta() * cantidad;
    }
}
