package Main.Model;

public class RapidoExpress implements ServicioDomicilio {
    private String nombre;
    private double tarifaBase;
    private double tarifaPorKm;
    private int kmMaximo;

    /**
     * Crea un servicio de domicilio con sus tarifas y distancia máxima permitida.
     *
     * @param nombre Nombre del servicio de domicilio.
     * @param tarifaBase Tarifa fija inicial del servicio.
     * @param tarifaPorKm Tarifa adicional por cada kilómetro recorrido.
     * @param kmMaximo Distancia máxima permitida para realizar una entrega.
     */
    public RapidoExpress(String nombre, double tarifaBase, double tarifaPorKm, int kmMaximo) {

        this.nombre = nombre;
        this.tarifaBase = tarifaBase;
        this.tarifaPorKm = tarifaPorKm;
        this.kmMaximo = kmMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Verifica si la distancia solicitada es válida para el servicio,
     * teniendo en cuenta que debe ser positiva y no superar el límite permitido.
     *
     * @param km Distancia del recorrido en kilómetros.
     * @return true si el recorrido está dentro del rango permitido; false en caso contrario.
     */
    @Override
    public boolean aceptaRecorrido(double km) {
        return km > 0 && km <= kmMaximo;
    }

    /**
     * Calcula el costo del servicio de domicilio sumando la tarifa base
     * y el valor correspondiente a los kilómetros recorridos.
     *
     * @param km Distancia del recorrido en kilómetros.
     * @return Costo total del servicio si acepta el recorrido; 0 en caso contrario.
     */
    @Override
    public double calcularCosto(double km) {
        if (!aceptaRecorrido(km)) return 0;
        return tarifaBase + tarifaPorKm * km;
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