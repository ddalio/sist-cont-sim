package simulacion.continuo;

import simulacion.continuo.configuracion.Configuracion;

import java.util.Map;
import java.util.HashMap;

public class SistemaContinuo {
    Estado estado;
    double h = 0.001;
    double t_max = 50.0;
    double tolerance = 0.001;
    double tiempo = 0.0;

    public SistemaContinuo(Estado estado) {
        this.estado = estado;
    }

    public void cargarParametros(Configuracion config) {
        this.h = config.h();
        this.t_max = config.t_max();
        this.tolerance = config.tolerance();
    }

    public void run() {
        while (tiempo < t_max) {
            estado.paso(h);
            tiempo += h;
            if (tiempo % 1.0 < h) {
                System.out.printf("Tiempo: %.2f | ", tiempo);
                variables.forEach(v -> System.out.printf("%s: %.4f ", v.getNombre(), v.getValor()));
                System.out.println();
            }
        }
    }
}
