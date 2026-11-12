package Main.Model;

public class RapidoExpress implements ServicioDomicilio{
    private String nombre;
    private double tarifaBase;
    private double tarifaPorKm;
    private int kmMaximo;

    public RapidoExpress(String nombre, double tarifaBase, double tarifaPorKm, int kmMaximo) {

        this.nombre = nombre;
        this.tarifaBase = tarifaBase;
        this.tarifaPorKm = tarifaPorKm;
        this.kmMaximo = kmMaximo;
    }
    public String nombreEmpresa() { return "RápidoExpress"; }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean aceptaRecorrido(double km) {
        return km > 0 && km <= kmMaximo;
    }

    @Override
    public double calcularCosto(double km) {
        if (!aceptaRecorrido(km)) return 0;
        return tarifaBase + tarifaPorKm * km;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getTarifaPorKm() {
        return tarifaPorKm;
    }

    public void setTarifaPorKm(double tarifaPorKm) {
        this.tarifaPorKm = tarifaPorKm;
    }

    public int getKmMaximo() {
        return kmMaximo;
    }

    public void setKmMaximo(int kmMaximo) {
        this.kmMaximo = kmMaximo;
    }

    @Override
    public String toString() {
        return "RapidoExpress{" +
                "nombre='" + nombre + '\'' +
                ", tarifaBase=" + tarifaBase +
                ", tarifaPorKm=" + tarifaPorKm +
                ", kmMaximo=" + kmMaximo +
                '}';
    }
}
