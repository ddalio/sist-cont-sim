package simulacion.continuo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import simulacion.continuo.model.configuracion.Configuracion;
import simulacion.continuo.model.configuracion.ConfiguracionLoader;

/**
 * Servicio encargado de ejecutar la simulación en un hilo separado.
 * Esta clase es pura de dominio y no tiene dependencias de UI.
 */
public class SimuladorService {

    public interface SimulacionCallback {
        void alFinalizar(ResultadoSimulacion resultado);
    }

    public void ejecutar(Map<String, Double> parametros, boolean esLineal,boolean usarRK4, SimulacionCallback callback) {
        Thread hiloCalculo = new Thread(() -> {
            Configuracion config = ConfiguracionLoader.load();
            SistemaContinuo sistema =SistemaFactory.crear(
        parametros,
        esLineal,
        usarRK4,
        config
    );
            
            final double h = config.h();
            final double tMax = config.t_max();

            List<Double> tiempos = new ArrayList<>();
            List<Double> posiciones = new ArrayList<>();
            List<Double> velocidades = new ArrayList<>();

            double t = 0.0;
            Variable posVar = sistema.getVariables().stream().filter(v -> v.getNombre().equals("posicion")).findFirst().orElseThrow();
            Variable velVar = sistema.getVariables().stream().filter(v -> v.getNombre().equals("velocidad")).findFirst().orElseThrow();
            int pasosEnEquilibrio = 0;
            
            while (t < tMax) {
                sistema.paso();
                t = sistema.getTiempo();

                double x = posVar.getValor();
                double v = velVar.getValor();

                if (Math.abs(x) < 0.001 && Math.abs(v) < 0.001) {
                    pasosEnEquilibrio++;
                } else {
                    pasosEnEquilibrio = 0;
                }

                if (pasosEnEquilibrio >= 100) {
                    break;
                }

                if (Math.round(t / h) % 10 == 0) {
                    tiempos.add(t);
                    posiciones.add(posVar.getValor());
                    velocidades.add(velVar.getValor());
                }
            }

            ResultadoSimulacion resultado = new ResultadoSimulacion(tiempos, posiciones, velocidades, t);
            callback.alFinalizar(resultado);
        });

        hiloCalculo.setDaemon(true);
        hiloCalculo.start();
    }
}
