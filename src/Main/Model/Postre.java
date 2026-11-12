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
                "pesoGramos=" + pesoGramos +
                ", temperaturaAlm=" + temperaturaAlm +
                ", requiereRefrigerar=" + requiereRefrigerar +
                '}';
    }
}