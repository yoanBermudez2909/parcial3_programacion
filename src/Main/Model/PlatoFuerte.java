package Main.Model;

public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipo;
    private boolean coccionAprobada;
    private CategoriaProducto categoriaProducto;

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double pesoGramos, TipoPreparacion tipo, boolean coccionAprobada, int cantidadProducto,CategoriaProducto categoriaProducto) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion, cantidadProducto);
        this.pesoGramos = pesoGramos;
        this.tipo = tipo;
        this.coccionAprobada = coccionAprobada;
        this.categoriaProducto = categoriaProducto;
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
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }

    @Override
    public boolean cumpleCalidad() {
        boolean pesoOk = pesoGramos >= 200 && pesoGramos <= 900;
        boolean tiempoOk = getTiempoPreparacion() >= 10 && getTiempoPreparacion() <= 60;
        return pesoOk && tiempoOk && coccionAprobada;
    }

    @Override
    public CategoriaProducto obtenerCategoria() {
        return CategoriaProducto.PLATO_FUERTE;
    }

    @Override
    public String toString() {
        return "PlatoFuerte{" +
                "pesoGramos=" + pesoGramos +
                ", tipo=" + tipo +
                ", coccionAprobada=" + coccionAprobada +
                '}';
    }
}
