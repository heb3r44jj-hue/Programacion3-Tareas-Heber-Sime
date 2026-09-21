import java.util.Scanner;

public class BuscadorProductos {

    static int buscarProducto(String[] productos, String busqueda) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].toLowerCase().contains(busqueda.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public static void ejecutar(Scanner sc) {
        System.out.println("\n===== BUSQUEDA DE PRODUCTOS =====");
        String[] busquedas = {"lap", "monitor", "impresora"};

        for (int k = 0; k < busquedas.length; k++) {
            String busqueda = busquedas[k];
            int pos = buscarProducto(Main.productos, busqueda);
            System.out.println("Buscando: \"" + busqueda + "\"");
            if (pos != -1) {
                System.out.println("  Encontrado: " + Main.productos[pos] + " | Precio: $" + String.format("%,.2f", Main.precios[pos]) + " | Cantidad: " + Main.cantidades[pos]);
            } else {
                System.out.println("  No se encontro ningun producto");
            }
        }

        System.out.println("\n===== REEMPLAZO DE PRODUCTO =====");
        reemplazarProducto(sc);
    }

    static void reemplazarProducto(Scanner sc) {
        System.out.print("Ingresa el nombre (o parte del nombre) del producto a modificar: ");
        String busqueda = sc.nextLine();

        int pos = buscarProducto(Main.productos, busqueda);

        if (pos == -1) {
            System.out.println("  No se encontro ningun producto con ese nombre.");
            return;
        }

        System.out.println("  Encontrado: " + Main.productos[pos]+ " | Precio actual: $" + String.format("%,.2f", Main.precios[pos])
+ " | Cantidad actual: " + Main.cantidades[pos]);

        System.out.print("Nuevo precio (-1 para no cambiarlo): ");
        double nuevoPrecio = sc.nextDouble();
        if (nuevoPrecio >= 0) {
            Main.precios[pos] = nuevoPrecio;
        }

        System.out.print("Nueva cantidad (-1 para no cambiarla): ");
        int nuevaCantidad = sc.nextInt();
        if (nuevaCantidad >= 0) {
            Main.cantidades[pos] = nuevaCantidad;
        }

        System.out.println("  Producto actualizado: " + Main.productos[pos]
                + " | Precio: $" + String.format("%,.2f", Main.precios[pos])
                + " | Cantidad: " + Main.cantidades[pos]);
    }
}