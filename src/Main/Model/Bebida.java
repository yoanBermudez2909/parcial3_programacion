package Main.Model;

public class Bebida extends Producto {

    private int volumenMl;
    private double temperatura;
    private boolean esFria;
    private boolean prepEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, int volumenMl, double temperatura, boolean esFria, boolean prepEspecial, int cantidadProducto) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion, cantidadProducto);
        this.volumenMl = volumenMl;
        this.temperatura = temperatura;
        this.esFria = esFria;
        this.prepEspecial = prepEspecial;
    }


    public double calcularPrecioVenta() {
        return 0;
    }

    @Override
    public boolean cumpleCalidad() {
        return false;
    }

    @Override
    public String obtenerCategoria() {
        return "";
    }

    public int getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(int volumenMl) {
        this.volumenMl = volumenMl;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public boolean isEsFria() {
        return esFria;
    }

    public void setEsFria(boolean esFria) {
        this.esFria = esFria;
    }

    public boolean isPrepEspecial() {
        return prepEspecial;
    }

    public void setPrepEspecial(boolean prepEspecial) {
        this.prepEspecial = prepEspecial;
    }



    @Override
    public String toString() {
        return "Bebida{" +
                "volumenMl=" + volumenMl +
                ", temperatura=" + temperatura +
                ", esFria=" + esFria +
                ", prepEspecial=" + prepEspecial +
                '}';
    }
}