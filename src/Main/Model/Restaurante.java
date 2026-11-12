
package Main.Model;

import java.time.LocalDate;
import java.util.*;

public class Restaurante {
    private String direccion;
    private String NIT;
    private String nombreComercial;

    private List<Cliente> listClientes;
    private List<Producto> listProductos;
    private List<Pedido> listPedidos;

    /**
     * Crea un restaurante con sus datos de identificación e inicializa
     * las listas de clientes, productos y pedidos.
     *
     * @param direccion Dirección física del restaurante.
     * @param NIT Número de identificación tributaria del restaurante.
     * @param nombreComercial Nombre comercial del restaurante.
     */
    public Restaurante(String direccion, String NIT, String nombreComercial) {
        this.listPedidos = new ArrayList<>();
        this.listProductos = new ArrayList<>();
        this.listClientes = new ArrayList<>();
        this.direccion = direccion;
        this.NIT = NIT;
        this.nombreComercial = nombreComercial;
    }

    /**
     * Busca un producto registrado en el restaurante mediante su código.
     *
     * @param codigo Código identificador del producto que se desea buscar.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscarProducto(String codigo) {
        for (Producto p : listProductos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    /**
     * Busca un cliente registrado en el restaurante mediante su documento.
     *
     * @param documento Documento de identificación del cliente.
     * @return Cliente encontrado o null si no existe.
     */
    public Cliente buscarCliente(String documento) {
        for (Cliente c : listClientes) {
            if (c.getDocumento().equals(documento)) return c;
        }
        return null;
    }

    /**
     * Busca un pedido registrado en el restaurante mediante su código.
     *
     * @param codigo Código identificador del pedido que se desea buscar.
     * @return Pedido encontrado o null si no existe.
     */
    public Pedido buscarPedido(String codigo) {
        for (Pedido p : listPedidos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    /**
     * Registra un producto en el restaurante, verificando que no sea nulo,
     * que sus datos sean válidos y que no exista otro producto con el mismo código.
     *
     * @param producto Producto que se desea registrar.
     * @return true si el producto se registra correctamente; false si no cumple las condiciones.
     */
    public boolean registrarProducto(Producto producto) {
        if (producto == null || !producto.datosValidos() || buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        listProductos.add(producto);
        return true;
    }

    /**
     * Registra un cliente en el restaurante, evitando que existan clientes
     * con el mismo documento de identificación.
     *
     * @param cliente Cliente que se desea registrar.
     * @return true si el cliente se registra correctamente; false si es nulo o ya existe.
     */
    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getDocumento()) != null) {
            return false;
        }
        listClientes.add(cliente);
        return true;
    }

    /**
     * Registra un nuevo pedido asociado a un cliente existente,
     * verificando que sus datos básicos sean válidos y que su código no esté registrado.
     *
     * @param codigo Código identificador del nuevo pedido.
     * @param fecha Fecha de realización del pedido.
     * @param documento Documento de identificación del cliente.
     * @param modalidad Modalidad de entrega del pedido.
     * @return true si el pedido se registra correctamente; false si alguna validación falla.
     */
    public boolean registrarPedido(String codigo, LocalDate fecha, String documento, ModalidadEntrega modalidad) {
        if (codigo == null || fecha == null || modalidad == null){
            return false;
        }
        Cliente cliente = buscarCliente(documento);
        if (cliente == null){
            return false;
        }
        if (buscarPedido(codigo) != null){
            return false;
        }
        listPedidos.add(new Pedido(codigo, fecha, modalidad, cliente));
        return true;
    }

    /**
     * Consulta el historial de pedidos de un cliente registrado.
     *
     * @param documentoCliente Documento de identificación del cliente.
     * @return Lista con el historial de pedidos del cliente o null si no está registrado.
     */
    public List<Pedido> consultarHistorial(String documentoCliente) {
        Cliente cliente = buscarCliente(documentoCliente);
        if (cliente == null) {
            return null;
        }
        return cliente.consultarHistorial();
    }

    /**
     * Agrega un producto y su cantidad correspondiente a un pedido existente,
     * verificando previamente que ambos estén registrados.
     *
     * @param codigoPedido Código identificador del pedido.
     * @param codigoProducto Código identificador del producto.
     * @param cantidad Cantidad de unidades que se desean agregar.
     * @return true si el detalle se agrega correctamente; false si el pedido o producto no existen o la operación falla.
     */
    public boolean agregarProductoAPedido(String codigoPedido, String codigoProducto, int cantidad) {
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            return false;
        }
        Producto producto = buscarProducto(codigoProducto);
        if (producto == null) {
            return false;
        }
        return pedido.agregarDetalle(producto, cantidad);
    }

    /**
     * Obtiene los productos que cumplen las condiciones de calidad
     * y cuentan con al menos una unidad disponible.
     *
     * @return Lista de productos disponibles que cumplen las condiciones de calidad.
     */
    public List<Producto> consultarProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto producto : listProductos) {
            if (producto.cumpleCalidad() && producto.hayExistencias(1)) {
                disponibles.add(producto);
            }
        }
        return disponibles;
    }

    /**
     * Obtiene los productos registrados que no cumplen las condiciones de calidad.
     *
     * @return Lista de productos que no cumplen las condiciones de calidad.
     */
    public List<Producto> consultarProductosNoCumplenCalidad() {
        List<Producto> noCumplen = new ArrayList<>();
        for (Producto producto : listProductos) {
            if (!producto.cumpleCalidad()) {
                noCumplen.add(producto);
            }
        }
        return noCumplen;
    }

    /**
     * Confirma un pedido verificando que exista, que su cliente esté registrado,
     * que sus productos sigan registrados y que el pedido no haya sido agregado
     * previamente al historial del cliente.
     *
     * @param codigoPedido Código identificador del pedido que se desea confirmar.
     * @return true si el pedido se confirma y se agrega al historial del cliente; false si alguna validación falla.
     */
    public boolean confirmarPedido(String codigoPedido) {
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            return false;
        }
        Cliente cliente = buscarCliente(pedido.getCliente().getDocumento());
        if (cliente == null) {
            return false;
        }
        for (DetallePedido detalle : pedido.getListDetalles()) {
            if (buscarProducto(detalle.producto().getCodigo()) == null) {
                return false;
            }
        }
        if (cliente.contienePedido(codigoPedido)) {
            return false;
        }
        if (pedido.getCliente() != cliente) {
            return false;
        }
        if (!pedido.confirmar()) {
            return false;
        }
        return cliente.agregarPedido(pedido);
    }

    /**
     * Calcula los ingresos generados por la venta de productos,
     * considerando únicamente los pedidos que contabilizan como ingresos.
     *
     * @return Total de ingresos obtenidos por la venta de productos.
     */
    public double calcularIngresosProductos() {
        double total = 0;

        for (Pedido pedido : listPedidos) {
            if (pedido.contabilizaComoIngreso()) {
                total += pedido.calcularTotalProductos();
            }
        }

        return total;
    }

    /**
     * Calcula los ingresos generados por los servicios de domicilio,
     * considerando únicamente los pedidos que contabilizan como ingresos.
     *
     * @return Total de ingresos obtenidos por los servicios de domicilio.
     */
    public double calcularIngresosDomicilios() {
        double total = 0;
        for (Pedido pedido : listPedidos) {
            if (pedido.contabilizaComoIngreso()) {
                total += pedido.calcularCostoDomicilio();
            }
        }
        return total;
    }

    /**
     * Calcula los ingresos del restaurante a partir de la venta de productos.
     *
     * @return Total de ingresos generados por los productos vendidos.
     */
    public double calcularIngresos() {
        return calcularIngresosProductos();
    }

    /**
     * Agrupa y acumula las unidades vendidas de cada producto,
     * considerando únicamente los pedidos que contabilizan como ingresos.
     *
     * @return Mapa que relaciona el código de cada producto con su cantidad total vendida.
     */
    private Map<String, Integer> unidadesVendidasPorProducto() {
        Map<String, Integer> mapa = new HashMap<>();
        for (Pedido pedido : listPedidos) {
            if (pedido.contabilizaComoIngreso()) {
                for (DetallePedido detalle : pedido.getListDetalles()) {
                    String codigo = detalle.producto().getCodigo();
                    if (mapa.containsKey(codigo)) {
                        mapa.put(codigo, mapa.get(codigo) + detalle.cantidad());
                    } else {
                        mapa.put(codigo, detalle.cantidad());
                    }
                }
            }
        }
        return mapa;
    }

    /**
     * Identifica el producto o los productos con mayor cantidad de unidades vendidas.
     *
     * @return Mapa con los productos más vendidos y sus cantidades; vacío si no existen ventas.
     */
    public Map<Producto, Integer> productoMasVendido() {
        Map<String, Integer> unidades = unidadesVendidasPorProducto();
        Map<Producto, Integer> resultado = new LinkedHashMap<>();
        if (unidades.isEmpty()) {
            return resultado;
        }
        int maximo = 0;
        for (Integer cantidad : unidades.values()) {
            if (cantidad > maximo) {
                maximo = cantidad;
            }
        }
        for (String codigo : unidades.keySet()) {
            if (unidades.get(codigo) == maximo) {
                resultado.put(buscarProducto(codigo), maximo);
            }
        }
        return resultado;
    }

    /**
     * Calcula los ingresos generados por cada categoría de producto,
     * acumulando los subtotales de los detalles de pedidos que contabilizan como ingresos.
     *
     * @return Mapa que relaciona cada categoría de producto con sus ingresos totales.
     */
    public Map<CategoriaProducto, Double> ingresosPorCategoria() {
        Map<CategoriaProducto, Double> mapa = new HashMap<>();

        for (Pedido pedido : listPedidos) {
            if (pedido.contabilizaComoIngreso()) {
                for (DetallePedido detalle : pedido.getListDetalles()) {
                    CategoriaProducto categoria = detalle.producto().obtenerCategoria();

                    if (mapa.containsKey(categoria)) {
                        mapa.put(categoria, mapa.get(categoria) + detalle.calcularSubTotal());
                    } else {
                        mapa.put(categoria, detalle.calcularSubTotal());
                    }
                }
            }
        }

        return mapa;
    }

    /**
     * Identifica la categoría de productos que genera mayores ingresos.
     *
     * @return Categoría con mayores ingresos o null si no existen ingresos por categoría.
     */
    public CategoriaProducto categoriaMasIngresos() {
        Map<CategoriaProducto, Double> ingresos = ingresosPorCategoria();

        if (ingresos.isEmpty()) {
            return null;
        }

        CategoriaProducto mayor = null;
        double maximo = 0;

        for (CategoriaProducto categoria : ingresos.keySet()) {
            if (mayor == null || ingresos.get(categoria) > maximo) {
                maximo = ingresos.get(categoria);
                mayor = categoria;
            }
        }

        return mayor;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cliente> getListClientes() {
        return new ArrayList<>(this.listClientes);
    }

    public List<Producto> getListProductos() {
        return new ArrayList<>(this.listProductos);
    }

    public List<Pedido> getListPedidos() {
        return new ArrayList<>(this.listPedidos);
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "nombreComercial='" + nombreComercial + '\'' +
                ", NIT='" + NIT + '\'' +
                ", direccion='" + direccion + '\'' +
                ", listClientes=" + listClientes +
                ", listProductos=" + listProductos +
                ", listPedidos=" + listPedidos +
                '}';
    }
}

