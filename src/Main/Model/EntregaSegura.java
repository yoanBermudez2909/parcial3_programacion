
package Main.Model;

public class EntregaSegura implements ServicioDomicilio {
    private String nombre;
    private double tarifaBase;
    private double tarifaPorKm;
    private double recargo;
    private int umbralRecargoKm;
    private int kmMaximo;

    /**
     * Metodo constructor de la clase EntregaSegura
     * @param nombre del servicio de domicilio
     * @param tarifaBase del servicio
     * @param tarifaPorKm del servicio
     * @param recargo adicional del servicio
     * @param umbralRecargoKm para aplicar el recargo
     * @param kmMaximo permitidos para el recorrido
     */
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

    /**
     * Metodo que permite verificar si el servicio acepta el recorrido solicitado
     * @param km distancia del recorrido en kilometros
     * @return true si el recorrido es valido, false en caso contrario
     */
    @Override
    public boolean aceptaRecorrido(double km) {
        return km > 0 && km <= kmMaximo;
    }

    /**
     * Metodo que permite calcular el costo del servicio de domicilio
     * @param km distancia del recorrido en kilometros
     * @return costo total del servicio o 0 si el recorrido no es valido
     */
    @Override
    public double calcularCosto(double km) {
        if (!aceptaRecorrido(km)) return 0;
        double costo = tarifaBase + tarifaPorKm * km;
        if (km > umbralRecargoKm) {
            costo += recargo;
        }
        return costo;
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