package simulacion.continuo;

import java.util.List;
import simulacion.continuo.configuracion.Configuracion;

public class SistemaContinuo {
    Estado estado;
    double h = 0.001;
    double t_max = 50.0;
    double tolerance = 0.001;
    double tiempo = 0.0;

    public SistemaContinuo(Estado estado) {
        this.estado = estado;
    }

    public void agregarVariable(Variable variable) {
        // Implementación mínima para mantener compatibilidad si es necesario
        // En la versión actual, las variables suelen pasarse en el constructor del Estado
    }

    public double getTiempo() {
        return this.tiempo;
    }

    public List<Variable> getVariables() {
        return estado.getVariables();
    }

    public void cargarParametros(Configuracion config) {
        this.h = config.h();
        this.t_max = config.t_max();
        this.tolerance = config.tolerance();
    }

    public void paso() {
        estado.paso(h);
        tiempo += h;
    }

    public void run() {
        while (tiempo < t_max) {
            paso();
            if (tiempo % 1.0 < h) {
                System.out.printf("Tiempo: %.2f | ", tiempo);
                estado.getVariables().forEach(v -> System.out.printf("%s: %.4f ", v.getNombre(), v.getValor()));
                System.out.println();
            }
        }
    }
}
