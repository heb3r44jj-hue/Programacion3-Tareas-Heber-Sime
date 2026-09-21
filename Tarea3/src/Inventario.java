public class Inventario {

    public static void ejecutar() {
        System.out.println("\n===== INVENTARIO =====");
        mostrarInventario();

        int masCaro = 0;
        int masBarato = 0;
        for (int i = 1; i < Main.precios.length; i++) {
            if (Main.precios[i] > Main.precios[masCaro]) {
                masCaro = i;
            }
            if (Main.precios[i] < Main.precios[masBarato]) {
                masBarato = i;
            }
        }
        System.out.println("Producto mas caro: " + Main.productos[masCaro]
                + " ($" + String.format("%,.2f", Main.precios[masCaro]) + ")");
        System.out.println("Producto mas barato: " + Main.productos[masBarato]
                + " ($" + String.format("%,.2f", Main.precios[masBarato]) + ")");

        System.out.println("Productos con menos de 15 unidades:");
        for (int i = 0; i < Main.cantidades.length; i++) {
            if (Main.cantidades[i] < 15) {
                System.out.println("  " + Main.productos[i]+ ": " + Main.cantidades[i] + " unidades");
            }
        }

        for (int i = 0; i < Main.precios.length - 1; i++) {
            for (int j = 0; j < Main.precios.length - 1 - i; j++) {
                if (Main.precios[j] < Main.precios[j + 1]) {
                    double tempP = Main.precios[j];
                    Main.precios[j] = Main.precios[j + 1];
                    Main.precios[j + 1] = tempP;

                    int tempC = Main.cantidades[j];
                    Main.cantidades[j] = Main.cantidades[j + 1];
                    Main.cantidades[j + 1] = tempC;

                    String tempN = Main.productos[j];
                    Main.productos[j] = Main.productos[j + 1];
                    Main.productos[j + 1] = tempN;
                }
            }
        }

        System.out.println("\nInventario ordenado de mayor a menor precio:");
        mostrarInventario();
    }

    static void mostrarInventario() {
        double totalGeneral = 0;
        System.out.println(String.format("%-15s %12s %10s %15s",
                "PRODUCTO", "PRECIO", "CANTIDAD", "VALOR TOTAL"));
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < Main.productos.length; i++) {
            double valor = Main.precios[i] * Main.cantidades[i];
            totalGeneral = totalGeneral + valor;
            System.out.println(String.format("%-15s %,12.2f %10d %,15.2f", Main.productos[i], Main.precios[i],Main.cantidades[i], valor));
        }
        System.out.println("----------------------------------------------------");
        System.out.println("TOTAL GENERAL: $" + String.format("%,.2f", totalGeneral));
    }
}