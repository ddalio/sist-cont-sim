package simulacion.continuo;

import java.util.HashMap;
import java.util.List;
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

    public void agregarVariable(Variable variable) {
        this.estado.getVariables().add(variable);
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
