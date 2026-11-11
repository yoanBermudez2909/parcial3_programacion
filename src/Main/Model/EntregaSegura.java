package Main.Model;

public class EntregaSegura {
    private String nombre;
    private double tarifaBase;
    private double tarifaPorKm;
    private double recargo;
    private int umbralRecargoKm;
    private int kmMaximo;

    public EntregaSegura(String nombre, double tarifaBase, double tarifaPorKm, double recargo, int umbralRecargoKm, int kmMaximo) {
        this.nombre = nombre;
        this.tarifaBase = tarifaBase;
        this.tarifaPorKm = tarifaPorKm;
        this.recargo = recargo;
        this.umbralRecargoKm = umbralRecargoKm;
        this.kmMaximo = kmMaximo;
    }

    public String getNombre() {
        return nombre;
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

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    public int getUmbralRecargoKm() {
        return umbralRecargoKm;
    }

    public void setUmbralRecargoKm(int umbralRecargoKm) {
        this.umbralRecargoKm = umbralRecargoKm;
    }

    public int getKmMaximo() {
        return kmMaximo;
    }

    public void setKmMaximo(int kmMaximo) {
        this.kmMaximo = kmMaximo;
    }

    @Override
    public String toString() {
        return "EntregaSegura{" +
                "nombre='" + nombre + '\'' +
                ", tarifaBase=" + tarifaBase +
                ", tarifaPorKm=" + tarifaPorKm +
                ", recargo=" + recargo +
                ", umbralRecargoKm=" + umbralRecargoKm +
                ", kmMaximo=" + kmMaximo +
                '}';
    }
}
