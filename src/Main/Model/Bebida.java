
package Main.Model;

public class Bebida extends Producto {

    private int volumenMl;
    private double temperatura;
    private boolean esFria;
    private boolean prepEspecial;

    /**
     * Metodo constructor de la clase Bebida
     *
     * @param codigo            de la bebida
     * @param nombre            de la bebida
     * @param descripcion       de la bebida
     * @param precioBase        de la bebida
     * @param tiempoPreparacion de la bebida
     * @param volumenMl         de la bebida
     * @param temperatura       de la bebida
     * @param esFria            indica si la bebida es fria
     * @param prepEspecial      indica si requiere preparacion especial
     * @param cantidadProducto  disponible de la bebida
     */
    public Bebida(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, int volumenMl, double temperatura, boolean esFria, boolean prepEspecial, int cantidadProducto) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion, cantidadProducto);
        this.volumenMl = volumenMl;
        this.temperatura = temperatura;
        this.esFria = esFria;
        this.prepEspecial = prepEspecial;
    }

    /**
     * Metodo que permite calcular el precio de venta de la bebida
     *
     * @return precio de venta de la bebida
     */
    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.10;
        if (prepEspecial) {
            precio += 3000;
        }
        return precio;
    }

    /**
     * Metodo que permite verificar si la bebida cumple con los parametros de calidad
     *
     * @return true si la bebida cumple con los parametros de calidad, false en caso contrario
     */
    @Override
    public boolean cumpleCalidad() {
        boolean volumenOk = volumenMl >= 150 && volumenMl <= 1500;
        boolean temperaturaOk;
        if (esFria) {
            temperaturaOk = temperatura >= 0 && temperatura <= 10;
        } else {
            temperaturaOk = temperatura >= 55 && temperatura <= 85;
        }
        return volumenOk && temperaturaOk;
    }

    /**
     * Metodo que permite obtener la categoria de la bebida
     *
     * @return categoria BEBIDA
     */
    @Override
    public CategoriaProducto obtenerCategoria() {
        return CategoriaProducto.BEBIDA;
    }

    @Override
    public String toString() {
        return "Bebida{" +
                "codigo=" + getCodigo() +
                ", nombre=" + getNombre() +
                ", precioVenta=" + calcularPrecioVenta() +
                "volumenMl=" + volumenMl +
                ", temperatura=" + temperatura +
                ", esFria=" + esFria +
                ", prepEspecial=" + prepEspecial +
                '}';
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
}
