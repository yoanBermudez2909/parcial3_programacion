package Main.Model;

public interface ServicioDomicilio {

    /**
     * Obtiene el nombre del servicio de domicilio.
     *
     * @return Nombre del servicio.
     */
    String getNombre();

    /**
     * Verifica si el servicio permite realizar un recorrido
     * según la distancia solicitada.
     *
     * @param km Distancia del recorrido en kilómetros.
     * @return true si el recorrido es aceptado; false en caso contrario.
     */
    boolean aceptaRecorrido(double km);

    /**
     * Calcula el costo del servicio de domicilio para una distancia determinada.
     *
     * @param km Distancia del recorrido en kilómetros.
     * @return Costo del servicio.
     */
    double calcularCosto(double km);

    /**
     * Solicita un servicio de domicilio verificando si acepta el recorrido.
     * Si lo acepta, calcula su costo; de lo contrario, genera un resultado
     * indicando que el servicio no está disponible para esa distancia.
     *
     * @param km Distancia del recorrido en kilómetros.
     * @return Resultado del servicio con su nombre, distancia, costo y estado de aceptación.
     */
    default ResultadoDomicilio solicitarServicio(double km) {
        if (!aceptaRecorrido(km)) {
            return new ResultadoDomicilio(getNombre(), km, 0, false);
        }
        return new ResultadoDomicilio(getNombre(), km, calcularCosto(km), true);
    }
}