public class Mantenimiento extends Empleado {
    private String area;

    public Mantenimiento(String codigo, String nombre, String apellido,double salario, String area) {
        super(codigo, nombre, apellido, salario);
        this.area = area;
    }

    @Override
    public void trabajar() {
        System.out.println("  Da mantenimiento al area de " + area);
    }

    @Override
    public String toString() {
        return super.toString() + " | Area: " + area;
    }
}