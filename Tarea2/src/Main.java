public class Main {
    public static void main(String[] args) {
        Empleado[] empleados = new Empleado[8];
        empleados[0] = new Profesor("P01", "Ana", "Garcia", 2500, "Matematicas", 18);
        empleados[1] = new Profesor("P02", "Luis", "Martinez", 2700, "Programacion", 20);
        empleados[2] = new Profesor("P03", "Carmen", "Rojas", 2400, "Fisica", 16);
        empleados[3] = new Administrativo("A01", "Pedro", "Lopez", 1800, "Registros", "Secretario");
        empleados[4] = new Administrativo("A02", "Maria", "Torres", 2000, "Finanzas", "Coordinador");
        empleados[5] = new Administrativo("A03", "Jorge", "Salas", 1900, "Admisiones", "Asistente");
        empleados[6] = new Mantenimiento("M01", "Carlos", "Perez", 1200, "Edificio A");
        empleados[7] = new Mantenimiento("M02", "Rosa", "Diaz", 1250, "Jardines");

        double totalSalarios = 0;
        Empleado mejorPagado = empleados[0];

        int cantProfesores = 0;
        int cantAdministrativos = 0;
        int cantMantenimiento = 0;

        System.out.println("EMPLEADOS ");
        for (int i = 0; i < empleados.length; i++) {
            Empleado e = empleados[i];

            System.out.println(e.toString());
            System.out.println("  Tipo real: " + e.getClass().getSimpleName());
            e.trabajar();

            if (e instanceof Bonificable) {
                Bonificable b = (Bonificable) e;
                System.out.println("  Bono: $" + b.calcularBono());
            } else {
                System.out.println("  No recibe bono");
            }

            totalSalarios = totalSalarios + e.getSalario();

            if (e.getSalario() > mejorPagado.getSalario()) {
                mejorPagado = e;
            }

            if (e instanceof Profesor) {
                cantProfesores++;
            } else if (e instanceof Administrativo) {
                cantAdministrativos++;
            } else if (e instanceof Mantenimiento) {
                cantMantenimiento++;
            }
        }

        System.out.println("RESUMEN");
        System.out.println("Total de salarios: $" + totalSalarios);
        System.out.println("Mejor pagado: " + mejorPagado.getNombre() + " "
                + mejorPagado.getApellido() + " ($" + mejorPagado.getSalario() + ")");
        System.out.println("Profesores: " + cantProfesores);
        System.out.println("Administrativos: " + cantAdministrativos);
        System.out.println("Mantenimiento: " + cantMantenimiento);
    }
}