package Main;

import Main.Model.*;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {



    private static final String TITULO = "El Buen Comer";



    private static final String MENU_PRINCIPAL = """
            MENÚ PRINCIPAL
 
            1. Productos del menú
            2. Registrar cliente
            3. Registrar y confirmar pedidos
            4. Consultar y actualizar pedidos
            5. Ventas del restaurante
            0. Salir
            """;

    private static final String MENU_PRODUCTOS = """
            PRODUCTOS DEL MENÚ
 
            1. Registrar producto
            2. Consultar productos disponibles
            3. Consultar productos que no cumplen calidad
            4. Actualizar existencias de un producto
            0. Volver
            """;

    private static final String MENU_REGISTRAR_PEDIDOS = """
            REGISTRAR Y CONFIRMAR PEDIDOS
 
            1. Crear pedido
            2. Agregar producto al pedido
            3. Asignar domicilio al pedido
            4. Confirmar pedido
            0. Volver
            """;

    private static final String MENU_CONSULTAR_PEDIDOS = """
            CONSULTAR Y ACTUALIZAR PEDIDOS
 
            1. Consultar pedido por código (con su valor total)
            2. Consultar historial de un cliente
            3. Cambiar la cantidad de un producto del pedido
            4. Quitar un producto del pedido
            5. Marcar pedido como entregado
            6. Cancelar pedido
            0. Volver
            """;


    private static Restaurante restaurante;
    private static RapidoExpress rapidoExpress;
    private static EntregaSegura entregaSegura;


    public static void main(String[] args) {
        restaurante = new Restaurante("Armenia, Quindío", "900123456-7", "El Buen Comer");


        rapidoExpress = new RapidoExpress("RápidoExpress", 5000, 1500, 12);
        entregaSegura = new EntregaSegura("EntregaSegura", 7000, 1000, 3000, 5, 20);


        Producto hamburguesa = new PlatoFuerte("P001", "Hamburguesa artesanal", "Carne, queso y vegetales", 18000, 20, 350, TipoPreparacion.A_LA_PLANCHA, true, 20);
        Producto limonada = new Bebida("B001", "Limonada natural", "Limonada con hielo", 5000, 5, 400, 4, true, false, 30);
        Producto brownie = new Postre("P002", "Brownie", "Brownie de chocolate", 7000, 10, 120, 5, true, 15);

        restaurante.registrarProducto(hamburguesa);
        restaurante.registrarProducto(limonada);
        restaurante.registrarProducto(brownie);


        Cliente cliente1 = new Cliente("1094900001", "Juan Pérez", "3001234567", "juan@gmail.com", "Calle 10 # 20-30");
        Cliente cliente2 = new Cliente("1094900002", "María Rodríguez", "3107654321", "maria@gmail.com", "Carrera 15 # 12-40");

        restaurante.registrarCliente(cliente1);
        restaurante.registrarCliente(cliente2);
        int opcion;
        do {
            opcion = leerOpcionMenu(MENU_PRINCIPAL, 5);
            switch (opcion) {
                case 1:
                    menuProductos();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    menuRegistrarPedidos();
                    break;
                case 4:
                    menuConsultarPedidos();
                    break;
                case 5:
                    consultarVentas();
                    break;
                case 0:
                    mostrarInfo("Gracias por usar el sistema de El Buen Comer.");
                    break;
            }
        } while (opcion != 0);
    }


    private static void menuProductos() {
        int opcion;
        do {
            opcion = leerOpcionMenu(MENU_PRODUCTOS, 4);
            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    mostrarInfo("PRODUCTOS DISPONIBLES\n\n"
                            + textoLista(restaurante.consultarProductosDisponibles()));
                    break;
                case 3:
                    mostrarInfo("PRODUCTOS QUE NO CUMPLEN LAS CONDICIONES DE CALIDAD\n\n"
                            + textoLista(restaurante.consultarProductosNoCumplenCalidad()));
                    break;
                case 4:
                    actualizarExistencias();
                    break;
            }
        } while (opcion != 0);
    }

    private static void menuRegistrarPedidos() {
        int opcion;
        do {
            opcion = leerOpcionMenu(MENU_REGISTRAR_PEDIDOS, 4);
            switch (opcion) {
                case 1:
                    crearPedido();
                    break;
                case 2:
                    agregarProductoAPedido();
                    break;
                case 3:
                    asignarDomicilio();
                    break;
                case 4:
                    confirmarPedido();
                    break;
            }
        } while (opcion != 0);
    }

    private static void menuConsultarPedidos() {
        int opcion;
        do {
            opcion = leerOpcionMenu(MENU_CONSULTAR_PEDIDOS, 6);
            switch (opcion) {
                case 1:
                    consultarPedido();
                    break;
                case 2:
                    consultarHistorialCliente();
                    break;
                case 3:
                    cambiarCantidad();
                    break;
                case 4:
                    quitarProducto();
                    break;
                case 5:
                    entregarPedido();
                    break;
                case 6:
                    cancelarPedido();  break;
            }
        } while (opcion != 0);
    }

    private static void registrarProducto() {
        Integer categoria = leerOpcion("""
                Categoría del producto:
 
                1. Plato fuerte
                2. Bebida
                3. Postre
                """, 1, 3);
        if (categoria == null) return;

        String codigo = leerTexto("Código del producto:");
        if (codigo == null) return;
        if (restaurante.buscarProducto(codigo) != null) {
            mostrarError("Ya existe un producto con el código " + codigo + ".");
            return;
        }
        String nombre = leerTexto("Nombre:");
        if (nombre == null) return;
        String descripcion = leerTexto("Descripción:");
        if (descripcion == null) return;
        Double precioBase = leerDecimal("Precio base (COP):");
        if (precioBase == null) return;
        Integer tiempo = leerEntero("Tiempo estimado de preparación (minutos):");
        if (tiempo == null) return;
        Integer existencias = leerEntero("Existencias (unidades disponibles):");
        if (existencias == null) return;

        Producto producto = null;
        switch (categoria) {
            case 1: {
                Double peso = leerDecimal("Peso (gramos):");
                if (peso == null) return;
                Integer tipoOpcion = leerOpcion("""
                        Tipo de preparación:
 
                        1. Asado
                        2. Frito
                        3. Horneado
                        4. A la plancha
                        """, 1, 4);
                if (tipoOpcion == null) return;
                TipoPreparacion tipo = TipoPreparacion.values()[tipoOpcion - 1];
                Boolean coccion = leerSiNo("¿Superó el control de cocción?");
                if (coccion == null) return;
                producto = new PlatoFuerte(codigo, nombre, descripcion, precioBase, tiempo,
                        peso, tipo, coccion, existencias);
                break;
            }
            case 2: {
                Integer volumen = leerEntero("Volumen (mililitros):");
                if (volumen == null) return;
                Double temperatura = leerDecimal("Temperatura de servicio (°C):");
                if (temperatura == null) return;
                Boolean esFria = leerSiNo("¿Es una bebida fría?");
                if (esFria == null) return;
                Boolean prepEspecial = leerSiNo("¿Requiere preparación especial (recargo fijo de $3.000)?");
                if (prepEspecial == null) return;
                producto = new Bebida(codigo, nombre, descripcion, precioBase, tiempo,
                        volumen, temperatura, esFria, prepEspecial, existencias);
                break;
            }
            case 3: {
                Double peso = leerDecimal("Peso (gramos):");
                if (peso == null) return;
                Double temperaturaAlm = leerDecimal("Temperatura de almacenamiento (°C):");
                if (temperaturaAlm == null) return;
                Boolean refrigerar = leerSiNo("¿Requiere refrigeración?");
                if (refrigerar == null) return;
                producto = new Postre(codigo, nombre, descripcion, precioBase, tiempo,
                        peso, temperaturaAlm, refrigerar, existencias);
                break;
            }
        }

        if (restaurante.registrarProducto(producto)) {
            mostrarInfo("Producto registrado:\n" + textoProducto(producto));
        } else {
            mostrarError("No se pudo registrar el producto. Revise que el precio y el tiempo sean mayores que 0.");
        }
    }

    private static void actualizarExistencias() {
        String codigo = leerTexto("Código del producto:");
        if (codigo == null) return;
        Producto producto = restaurante.buscarProducto(codigo);
        if (producto == null) {
            mostrarError("No existe un producto con el código " + codigo + ".");
            return;
        }
        Integer nuevas = leerEntero(producto.getNombre() + "\nExistencias actuales: "
                + producto.getCantidadProducto() + "\n\nNuevas existencias:");
        if (nuevas == null) return;
        if (producto.actualizarExistencias(nuevas)) {
            mostrarInfo("Existencias actualizadas:\n" + textoProducto(producto));
        } else {
            mostrarError("No se pudo actualizar: las existencias no pueden ser negativas.");
        }
    }

    private static void registrarCliente() {
        String documento = leerTexto("Número de documento:");
        if (documento == null) return;
        if (restaurante.buscarCliente(documento) != null) {
            mostrarError("Ya existe un cliente con el documento " + documento + ".");
            return;
        }
        String nombre = leerTexto("Nombre completo:");
        if (nombre == null) return;
        String telefono = leerTexto("Teléfono:");
        if (telefono == null) return;
        String correo = leerTexto("Correo electrónico:");
        if (correo == null) return;
        String direccion = leerTexto("Dirección de residencia:");
        if (direccion == null) return;

        Cliente cliente = new Cliente(documento, nombre, telefono, correo, direccion);
        if (restaurante.registrarCliente(cliente)) {
            mostrarInfo("Cliente registrado: " + nombre + " (" + documento + ")");
        } else {
            mostrarError("No se pudo registrar el cliente.");
        }
    }

    private static void crearPedido() {
        String codigo = leerTexto("Código del pedido:");
        if (codigo == null) return;
        String documento = leerTexto("Documento del cliente:");
        if (documento == null) return;
        Integer modalidadOpcion = leerOpcion("""
                Modalidad de entrega:
 
                1. Consumo en el restaurante
                2. Domicilio
                """, 1, 2);
        if (modalidadOpcion == null) return;
        ModalidadEntrega modalidad;
        if (modalidadOpcion == 1) {
            modalidad = ModalidadEntrega.CONSUMO_RESTAURANTE;
        } else {
            modalidad = ModalidadEntrega.DOMICILIO;
        }

        if (restaurante.registrarPedido(codigo, LocalDate.now(), documento, modalidad)) {
            mostrarInfo("Pedido " + codigo + " creado en estado PENDIENTE.\n"
                    + "Ahora agregue productos y, si es a domicilio, asigne la empresa.");
        } else {
            mostrarError("No se pudo crear el pedido: el código ya existe o el cliente no está registrado.");
        }
    }

    private static void agregarProductoAPedido() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;

        String codigoProducto = leerTexto("PRODUCTOS DISPONIBLES\n\n"
                + textoLista(restaurante.consultarProductosDisponibles())
                + "\nCódigo del producto a agregar:");
        if (codigoProducto == null) return;
        if (restaurante.buscarProducto(codigoProducto) == null) {
            mostrarError("El producto " + codigoProducto + " no pertenece al menú.");
            return;
        }
        Integer cantidad = leerEntero("Cantidad:");
        if (cantidad == null) return;

        if (restaurante.agregarProductoAPedido(pedido.getCodigo(), codigoProducto, cantidad)) {
            mostrarInfo("Producto agregado.\n\n" + textoPedido(pedido));
        } else {
            mostrarError("""
                    No se pudo agregar el producto. Verifique que:
                    - el pedido esté PENDIENTE,
                    - el producto cumpla las condiciones de calidad,
                    - la cantidad sea mayor que 0 y no supere las existencias
                      (contando lo que ya está en el pedido).""");
        }
    }

    private static void asignarDomicilio() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;
        if (pedido.getModalidad() != ModalidadEntrega.DOMICILIO) {
            mostrarError("El pedido " + pedido.getCodigo() + " es para consumo en el restaurante; no lleva domicilio.");
            return;
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            mostrarError("Solo se puede asignar domicilio a pedidos PENDIENTES. Estado actual: " + pedido.getEstado() + ".");
            return;
        }

        Integer empresa = leerOpcion("Empresa de domicilio:\n\n"
                + "1. " + rapidoExpress.getNombre() + " (recorridos hasta " + rapidoExpress.getKmMaximo() + " km)\n"
                + "2. " + entregaSegura.getNombre() + " (recorridos hasta " + entregaSegura.getKmMaximo() + " km)\n", 1, 2);
        if (empresa == null) return;
        Double km = leerDecimal("Distancia del recorrido (km):");
        if (km == null) return;

        ServicioDomicilio servicio = (empresa == 1) ? rapidoExpress : entregaSegura;
        if (pedido.asignarDomicilio(servicio, km)) {
            ResultadoDomicilio resultado = pedido.getDomicilio();
            mostrarInfo(resultado.nombreEmpresa() + " aceptó el recorrido de " + resultado.distanciaKm() + " km.\n"
                    + "Costo del domicilio: " + formatoDinero(resultado.costo()));
        } else {
            mostrarError(servicio.getNombre() + " no acepta ese recorrido ("
                    + "la distancia debe ser mayor que 0 y no superar su máximo). Pruebe con otra distancia o con la otra empresa.");
        }
    }

    private static void confirmarPedido() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;

        if (restaurante.confirmarPedido(pedido.getCodigo())) {
            mostrarInfo("Pedido confirmado.\n\n" + textoPedido(pedido));
        } else {
            mostrarError("""
                    No se pudo confirmar el pedido. Verifique que:
                    - esté PENDIENTE (no confirmado ni cancelado),
                    - tenga al menos un producto,
                    - todos sus productos cumplan la calidad y tengan existencias,
                    - si es a domicilio, una empresa haya aceptado el recorrido.""");
        }
    }



    private static void consultarPedido() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;
        mostrarInfo(textoPedido(pedido));
    }

    private static void consultarHistorialCliente() {
        String documento = leerTexto("Documento del cliente:");
        if (documento == null) return;

        List<Pedido> historial = restaurante.consultarHistorial(documento);
        if (historial == null) {
            mostrarError("No existe un cliente con el documento " + documento + ".");
            return;
        }
        String nombre = restaurante.buscarCliente(documento).getNombreCompleto();
        if (historial.isEmpty()) {
            mostrarInfo(nombre + " todavía no tiene pedidos confirmados.");
            return;
        }
        String texto = "HISTORIAL DE PEDIDOS DE " + nombre + "\n";
        for (Pedido pedido : historial) {
            texto += "\n" + pedido.resumen();
        }
        mostrarInfo(texto);
    }

    private static void cambiarCantidad() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;
        String codigoProducto = leerTexto("Código del producto dentro del pedido:");
        if (codigoProducto == null) return;
        Integer nueva = leerEntero("Nueva cantidad:");
        if (nueva == null) return;

        if (pedido.actualizarCantidad(codigoProducto, nueva)) {
            mostrarInfo("Cantidad actualizada.\n\n" + textoPedido(pedido));
        } else {
            mostrarError("""
                    No se pudo actualizar la cantidad. Verifique que:
                    - el pedido esté PENDIENTE,
                    - el producto esté en el pedido,
                    - la cantidad sea mayor que 0 y no supere las existencias.""");
        }
    }

    private static void quitarProducto() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;
        String codigoProducto = leerTexto("Código del producto a quitar:");
        if (codigoProducto == null) return;

        if (pedido.eliminarDetalle(codigoProducto)) {
            mostrarInfo("Producto quitado.\n\n" + textoPedido(pedido));
        } else {
            mostrarError("No se pudo quitar el producto: el pedido no está PENDIENTE o el producto no está en el pedido.");
        }
    }

    private static void entregarPedido() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;

        if (pedido.entregar()) {
            mostrarInfo("Pedido " + pedido.getCodigo() + " marcado como ENTREGADO.");
        } else {
            mostrarError("Solo los pedidos CONFIRMADOS pueden entregarse. Estado actual: " + pedido.getEstado() + ".");
        }
    }

    private static void cancelarPedido() {
        Pedido pedido = pedirPedido();
        if (pedido == null) return;

        if (pedido.cancelar()) {
            mostrarInfo("Pedido " + pedido.getCodigo() + " CANCELADO.");
        } else {
            mostrarError("Solo los pedidos PENDIENTES pueden cancelarse. Estado actual: " + pedido.getEstado() + ".");
        }
    }

    private static void consultarVentas() {
        String mensaje = "VENTAS DEL RESTAURANTE\n";

        mensaje += "\nIngresos del restaurante (productos): " + formatoDinero(restaurante.calcularIngresos());
        mensaje += "\nValor de domicilios (aparte, no es ingreso propio): "
                + formatoDinero(restaurante.calcularIngresosDomicilios());

        Map<Producto, Integer> masVendidos = restaurante.productoMasVendido();
        if (masVendidos.isEmpty()) {
            mensaje += "\n\nProducto más vendido: aún no hay ventas.";
        } else {
            mensaje += "\n\nProducto(s) más vendido(s):";
            for (Map.Entry<Producto, Integer> entrada : masVendidos.entrySet()) {
                mensaje += "\n- " + entrada.getKey().getNombre() + ": " + entrada.getValue() + " unidades";
            }
        }

        CategoriaProducto categoria = restaurante.categoriaMasIngresos();
        if (categoria == null) {
            mensaje += "\n\nCategoría con mayores ingresos: aún no hay ventas.";
        } else {
            mensaje += "\n\nCategoría con mayores ingresos: " + categoria + " ("
                    + formatoDinero(restaurante.ingresosPorCategoria().get(categoria)) + ")";
        }

        mostrarInfo(mensaje);
    }

    private static String textoProducto(Producto producto) {
        return producto.getCodigo() + " | " + producto.getNombre()
                + " | " + producto.obtenerCategoria()
                + " | Precio de venta: " + formatoDinero(producto.calcularPrecioVenta())
                + " | Existencias: " + producto.getCantidadProducto()
                + " | Calidad: " + (producto.cumpleCalidad() ? "cumple" : "NO cumple");
    }

    private static String textoLista(List<Producto> productos) {
        if (productos.isEmpty()) {
            return "(no hay productos)\n";
        }
        String texto = "";
        for (Producto producto : productos) {
            texto += textoProducto(producto) + "\n";
        }
        return texto;
    }

    private static String textoPedido(Pedido pedido) {
        String texto = "Pedido: " + pedido.getCodigo()
                + "\nFecha: " + pedido.getFecha()
                + "\nCliente: " + pedido.getCliente().getNombreCompleto()
                + " (" + pedido.getCliente().getDocumento() + ")"
                + "\nModalidad: " + pedido.getModalidad()
                + "\nEstado: " + pedido.getEstado()
                + "\n\nProductos:";

        if (pedido.getListDetalles().isEmpty()) {
            texto += "\n(sin productos)";
        }
        for (DetallePedido detalle : pedido.getListDetalles()) {
            texto += "\n- " + detalle.producto().getNombre() + " x" + detalle.cantidad()
                    + " = " + formatoDinero(detalle.calcularSubTotal());
        }

        texto += "\n\nTotal productos: " + formatoDinero(pedido.calcularTotalProductos());

        if (pedido.getModalidad() == ModalidadEntrega.DOMICILIO) {
            ResultadoDomicilio domicilio = pedido.getDomicilio();
            if (domicilio == null) {
                texto += "\nDomicilio: sin asignar";
            } else if (!domicilio.aceptado()) {
                texto += "\nDomicilio: " + domicilio.nombreEmpresa() + " no aceptó el recorrido";
            } else {
                texto += "\nDomicilio (" + domicilio.nombreEmpresa() + ", " + domicilio.distanciaKm() + " km): "
                        + formatoDinero(domicilio.costo());
            }
        }

        texto += "\nVALOR TOTAL: " + formatoDinero(pedido.calcularTotal());
        return texto;
    }

    private static String formatoDinero(double valor) {
        return String.format("$%,.2f", valor);
    }


    private static Pedido pedirPedido() {
        String codigo = leerTexto("Código del pedido:");
        if (codigo == null) return null;
        Pedido pedido = restaurante.buscarPedido(codigo);
        if (pedido == null) {
            mostrarError("No existe un pedido con el código " + codigo + ".");
        }
        return pedido;
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(null, mensaje, TITULO, JOptionPane.QUESTION_MESSAGE);
            if (entrada == null) return null;
            entrada = entrada.trim();
            if (!entrada.isEmpty()) return entrada;
            mostrarError("El campo no puede estar vacío.");
        }
    }

    private static Integer leerEntero(String mensaje) {
        while (true) {
            String entrada = leerTexto(mensaje);
            if (entrada == null) return null;
            if (entrada.matches("\\d{1,9}")) {
                return Integer.parseInt(entrada);
            }
            mostrarError("Ingrese un número entero, solo dígitos (sin signo ni decimales).");
        }
    }

    private static Double leerDecimal(String mensaje) {
        while (true) {
            String entrada = leerTexto(mensaje);
            if (entrada == null) return null;
            if (entrada.matches("-?\\d{1,9}([.,]\\d{1,2})?")) {
                return Double.parseDouble(entrada.replace(',', '.'));
            }
            mostrarError("Ingrese un número válido, por ejemplo 12500 o 7,5.");
        }
    }

    private static Integer leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            Integer opcion = leerEntero(mensaje);
            if (opcion == null) return null;
            if (opcion >= minimo && opcion <= maximo) return opcion;
            mostrarError("Elija una opción entre " + minimo + " y " + maximo + ".");
        }
    }

    private static Boolean leerSiNo(String pregunta) {
        Integer opcion = leerOpcion(pregunta + "\n\n1. Sí\n2. No", 1, 2);
        if (opcion == null) return null;
        return opcion == 1;
    }

    private static int leerOpcionMenu(String menu, int maximo) {
        Integer opcion = leerOpcion(menu, 0, maximo);
        if (opcion == null) return 0;
        return opcion;
    }


    private static void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.ERROR_MESSAGE);
    }
}