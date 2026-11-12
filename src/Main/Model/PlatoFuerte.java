package Main.Model;

public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipo;
    private boolean coccionAprobada;

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double pesoGramos, TipoPreparacion tipo, boolean coccionAprobada, int cantidadProducto) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion, cantidadProducto);
        this.pesoGramos = pesoGramos;
        this.tipo = tipo;
        this.coccionAprobada = coccionAprobada;
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
        return "PlatoFuerte{" +
                "pesoGramos=" + pesoGramos +
                ", tipo=" + tipo +
                ", coccionAprobada=" + coccionAprobada +
                '}';
    }
}
