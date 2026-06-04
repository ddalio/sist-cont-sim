package simulacion.continuo;

import java.util.HashMap;
import java.util.Map;

import simulacion.continuo.configuracion.Configuracion;

public class SistemaContinuo {
    Map<String, Double> parametros = new HashMap<>();
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

    public void paso() {
        for (Variable v : estado.getVariables()) {
            v.calcularDerivada(estado);
        }
        for (Variable v : estado.getVariables()) {
            v.avanzar(h);
        }
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
