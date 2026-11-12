
package Main.Model;

public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipo;
    private boolean coccionAprobada;

    /**
     * Metodo constructor de la clase PlatoFuerte
     * @param codigo del plato fuerte
     * @param nombre del plato fuerte
     * @param descripcion del plato fuerte
     * @param precioBase del plato fuerte
     * @param tiempoPreparacion del plato fuerte
     * @param pesoGramos del plato fuerte
     * @param tipo de preparacion del plato fuerte
     * @param coccionAprobada indica si la coccion fue aprobada
     * @param cantidadProducto disponible del plato fuerte
     */
    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double pesoGramos, TipoPreparacion tipo, boolean coccionAprobada, int cantidadProducto){

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion, cantidadProducto);
        this.pesoGramos = pesoGramos;
        this.tipo = tipo;
        this.coccionAprobada = coccionAprobada;
    }
    /**
     * Metodo que permite calcular el precio de venta del plato fuerte
     * @return precio de venta del plato fuerte
     */
    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }

    /**
     * Metodo que permite verificar si el plato fuerte cumple con los parametros de calidad
     * @return true si cumple con los parametros de calidad, false en caso contrario
     */
    @Override
    public boolean cumpleCalidad() {
        boolean pesoOk = pesoGramos >= 200 && pesoGramos <= 900;
        boolean tiempoOk = getTiempoPreparacion() >= 10 && getTiempoPreparacion() <= 60;
        return pesoOk && tiempoOk && coccionAprobada;
    }

    /**
     * Metodo que permite obtener la categoria del plato fuerte
     * @return categoria PLATO_FUERTE
     */
    @Override
    public CategoriaProducto obtenerCategoria() {
        return CategoriaProducto.PLATO_FUERTE;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoPreparacion tipo) {
        this.tipo = tipo;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }



    @Override
    public String toString() {
        return "PlatoFuerte{ "+
                " codigo=" + getCodigo() +
                ", nombre=" + getNombre() +
                ", precioVenta=" + calcularPrecioVenta() +
                "pesoGramos=" + pesoGramos +
                ", tipo=" + tipo +
                ", coccionAprobada=" + coccionAprobada +
                '}';
    }
}