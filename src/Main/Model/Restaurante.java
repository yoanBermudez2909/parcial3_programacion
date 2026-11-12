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

    public Restaurante(String direccion, String NIT, String nombreComercial) {
        this.listPedidos = new ArrayList<>();
        this.listProductos = new ArrayList<>();
        this.listClientes = new ArrayList<>();
        this.direccion = direccion;
        this.NIT = NIT;
        this.nombreComercial = nombreComercial;
    }


    public Producto buscarProducto(String codigo) {
        for (Producto p : listProductos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }


    public Cliente buscarCliente(String documento) {
        for (Cliente c : listClientes) {
            if (c.getDocumento().equals(documento)) return c;
        }
        return null;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido p : listPedidos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    public boolean registrarProducto(Producto producto) {
        if (producto == null || !producto.datosValidos() || buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        listProductos.add(producto);
        return true;
    }


    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getDocumento()) != null) {
            return false;
        }
        listClientes.add(cliente);
        return true;
    }


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

    public List<Pedido> consultarHistorial(String documentoCliente) {
        Cliente cliente = buscarCliente(documentoCliente);
        if (cliente == null) {
            return null;
        }
        return cliente.consultarHistorial();
    }

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

    public List<Producto> consultarProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto producto : listProductos) {
            if (producto.cumpleCalidad() && producto.hayExistencias(1)) {
                disponibles.add(producto);
            }
        }
        return disponibles;
    }

    public List<Producto> consultarProductosNoCumplenCalidad() {
        List<Producto> noCumplen = new ArrayList<>();
        for (Producto producto : listProductos) {
            if (!producto.cumpleCalidad()) {
                noCumplen.add(producto);
            }
        }
        return noCumplen;
    }

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

    public double calcularIngresosProductos() {
        double total = 0;

        for (Pedido pedido : listPedidos) {
            if (pedido.contabilizaComoIngreso()) {
                total += pedido.calcularTotalProductos();
            }
        }

        return total;
    }

    public double calcularIngresosDomicilios() {
        double total = 0;
        for (Pedido pedido : listPedidos) {
            if (pedido.contabilizaComoIngreso()) {
                total += pedido.calcularCostoDomicilio();
            }
        }
        return total;
    }

    public double calcularIngresos() {
        return calcularIngresosProductos();
    }

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

        public String getNombreComercial () {
            return nombreComercial;
        }

        public void setNombreComercial (String nombreComercial){
            this.nombreComercial = nombreComercial;
        }

        public String getNIT () {
            return NIT;
        }

        public void setNIT (String NIT){
            this.NIT = NIT;
        }

        public String getDireccion () {
            return direccion;
        }

        public void setDireccion (String direccion){
            this.direccion = direccion;
        }

        public List<Cliente> getListClientes () {
            return new ArrayList<>(this.listClientes);
        }



        public List<Producto> getListProductos () {
            return new ArrayList<>(this.listProductos);
        }


        public List<Pedido> getListPedidos () {
            return new ArrayList<>(this.listPedidos);
        }



        @Override
        public String toString () {
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
