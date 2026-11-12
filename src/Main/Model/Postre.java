package Main.Model;

public class Postre extends Producto {

    private double pesoGramos;
    private double temperaturaAlm;
    private boolean requiereRefrigerar;

    public Postre(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double pesoGramos, double temperaturaAlm, boolean requiereRefrigerar, int cantidadProducto) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion, cantidadProducto);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlm = temperaturaAlm;
        this.requiereRefrigerar = requiereRefrigerar;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.15;
        if (requiereRefrigerar) {
            precio += getPrecioBase() * 0.05;
        }
        return precio;
    }

    @Override
    public boolean cumpleCalidad() {
        boolean pesoOk = pesoGramos >= 80 && pesoGramos <= 500;
        boolean temperaturaOk;
        if (requiereRefrigerar) {
            temperaturaOk = temperaturaAlm >= 0 && temperaturaAlm <= 6;
        } else {
            temperaturaOk = temperaturaAlm >= 15 && temperaturaAlm <= 30;
        }
        return pesoOk && temperaturaOk;
    }

    @Override
    public CategoriaProducto obtenerCategoria() {
        return CategoriaProducto.POSTRE;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlm() {
        return temperaturaAlm;
    }

    public void setTemperaturaAlm(double temperaturaAlm) {
        this.temperaturaAlm = temperaturaAlm;
    }

    public boolean isRequiereRefrigerar() {
        return requiereRefrigerar;
    }

    public void setRequiereRefrigerar(boolean requiereRefrigerar) {
        this.requiereRefrigerar = requiereRefrigerar;
    }



    @Override
    public String toString() {
        return "Postre{" +
                "codigo=" + getCodigo() +
                ", nombre=" + getNombre() +
                ", precioVenta=" + calcularPrecioVenta() +
                ", volumenMl=" +
                "pesoGramos=" + pesoGramos +
                ", temperaturaAlm=" + temperaturaAlm +
                ", requiereRefrigerar=" + requiereRefrigerar +
                '}';
    }
}