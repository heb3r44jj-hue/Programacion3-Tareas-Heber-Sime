import java.io.Serializable;

public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula;
    private String nombre;
    private String carrera;
    private double calificacion;

    public Estudiante(String matricula, String nombre, String carrera, double calificacion) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.carrera = carrera;
        this.calificacion = calificacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isAprobado() {
        if (calificacion >= 70) {
            return true;
        } else {
            return false;
        }
    }

    public String getEstado() {
        if (isAprobado()) {
            return "Aprobado";
        } else {
            return "Reprobado";
        }
    }

    public String aLineaArchivo() {
        return matricula + ";" + nombre + ";" + carrera + ";" + calificacion;
    }

    @Override
    public String toString() {
        return matricula + " - " + nombre;
    }
}