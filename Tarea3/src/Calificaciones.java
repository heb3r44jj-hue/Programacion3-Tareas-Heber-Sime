public class Calificaciones {

    public static void ejecutar() {
        String[] nombres = {"Ana", "Luis", "Maria", "Carlos"};
        double[][] parciales = {
            {8.5, 9.0, 7.5},
            {6.0, 5.5, 7.0},
            {9.0, 8.5, 9.5},
            {4.5, 6.0, 5.0}
        };
        double[] promedios = new double[nombres.length];
        int aprobados = 0;
        int reprobados = 0;
        int mejor = 0;
        int peor = 0;

        System.out.println("\n===== CALIFICACIONES =====");
        System.out.println(String.format("%-10s %8s %8s %8s %10s %12s","NOMBRE", "P1", "P2", "P3", "PROMEDIO", "ESTADO"));

        for (int i = 0; i < nombres.length; i++) {
            double suma = 0;
            for (int j = 0; j < parciales[i].length; j++) {
                suma = suma + parciales[i][j];
            }
            promedios[i] = suma / parciales[i].length;

            String estado;
            if (promedios[i] >= 6.0) {
                estado = "Aprobado";
                aprobados++;
            } else {
                estado = "Reprobado";
                reprobados++;
            }

            if (promedios[i] > promedios[mejor]) {
                mejor = i;
            }
            if (promedios[i] < promedios[peor]) {
                peor = i;
            }

            System.out.println(String.format("%-10s %8.1f %8.1f %8.1f %10.2f %12s",nombres[i], parciales[i][0], parciales[i][1], parciales[i][2],promedios[i], estado));
        }

        System.out.println("\nPromedio de cada parcial:");
        for (int j = 0; j < 3; j++) {
            double suma = 0;
            for (int i = 0; i < nombres.length; i++) {
                suma = suma + parciales[i][j];
            }
            System.out.println("  Parcial " + (j + 1) + ": " + String.format("%.2f", suma / nombres.length));
        }

        System.out.println("Mejor estudiante: " + nombres[mejor] + " (" + String.format("%.2f", promedios[mejor]) + ")");
        System.out.println("Peor estudiante: " + nombres[peor]+ " (" + String.format("%.2f", promedios[peor]) + ")");
        System.out.println("Aprobados: " + aprobados);
        System.out.println("Reprobados: " + reprobados);
    }
}