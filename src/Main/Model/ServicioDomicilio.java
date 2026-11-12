package Main.Model;

public interface ServicioDomicilio {
    String getNombre();
    boolean aceptaRecorrido(double km);
    double calcularCosto(double km);
}