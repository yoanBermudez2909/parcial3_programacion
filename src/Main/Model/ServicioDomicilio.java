package Main.Model;

public interface ServicioDomicilio {
    String getNombre();
    boolean aceptaRecorrido(double km);
    double calcularCosto(double km);

    default ResultadoDomicilio solicitarServicio(double km) {
        if (!aceptaRecorrido(km)) {
            return new ResultadoDomicilio(getNombre(), km, 0, false);
        }
        return new ResultadoDomicilio(getNombre(), km, calcularCosto(km), true);
    }
}
