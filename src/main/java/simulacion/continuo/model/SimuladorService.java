package simulacion.continuo.model;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import simulacion.continuo.model.configuracion.Configuracion;
import simulacion.continuo.model.configuracion.ConfiguracionLoader;
import simulacion.continuo.model.metodos.IntegradorRK4;
import simulacion.continuo.model.terminos.Termino;
import simulacion.continuo.model.terminos.TerminoFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Servicio encargado de configurar y ejecutar la simulación en un hilo separado.
 */
public class SimuladorService {

    public interface SimulacionCallback {
        void alFinalizar(List<XYChart.Data<Number, Number>> pos, 
                         List<XYChart.Data<Number, Number>> vel, 
                         List<XYChart.Data<Number, Number>> fase, 
                         double tFinal);
    }

    public void ejecutar(Map<String, Double> parametros, boolean esLineal, SimulacionCallback callback) {
        Thread hiloCalculo = new Thread(() -> {
            // 1. Configuración de Términos
            TerminoFactory terminoFactory = new TerminoFactory(parametros);
            
            List<Termino> terminosVelocidad = esLineal ? 
                List.of(terminoFactory.crear("Gravedad"), terminoFactory.crear("Amortiguamiento"), terminoFactory.crear("ResorteLineal")) :
                List.of(terminoFactory.crear("Gravedad"), terminoFactory.crear("Amortiguamiento"), terminoFactory.crear("ResorteNoLineal"));
            
            List<Termino> terminosPosicion = List.of(terminoFactory.crear("VelocidadActual"));

            // 2. Creación de Variables y Estado
            Variable velocidad = new Variable("velocidad", 0.0, terminosVelocidad);
            Variable posicion = new Variable("posicion", 1.0, terminosPosicion);

            Map<String, Variable> mapaVariables = new HashMap<>();
            mapaVariables.put(velocidad.getNombre(), velocidad);
            mapaVariables.put(posicion.getNombre(), posicion);
            Estado estado = new Estado(mapaVariables);

            // 3. Inicialización del Sistema
            SistemaContinuo sistema = new SistemaContinuo(estado, new IntegradorRK4());
            Configuracion config = ConfiguracionLoader.load();
            sistema.cargarParametros(config);
            
            final double h = config.h();
            final double tMax = config.t_max();

            // 4. Bucle de Simulación
            List<XYChart.Data<Number, Number>> listaPosicion = new ArrayList<>();
            List<XYChart.Data<Number, Number>> listaVelocidad = new ArrayList<>();
            List<XYChart.Data<Number, Number>> listaFases = new ArrayList<>();

            double t = 0.0;
            while (t < tMax) {
                sistema.paso();
                t = sistema.getTiempo();

                double currentX = posicion.getValor();
                double currentV = velocidad.getValor();

                if (Math.round(t / h) % 10 == 0) {
                    listaPosicion.add(new XYChart.Data<>(t, currentX));
                    listaVelocidad.add(new XYChart.Data<>(t, currentV));
                    listaFases.add(new XYChart.Data<>(currentX, currentV));
                }
            }

            // 5. Notificar resultados
            final double tiempoFinal = t;
            Platform.runLater(() -> callback.alFinalizar(listaPosicion, listaVelocidad, listaFases, tiempoFinal));
        });

        hiloCalculo.setDaemon(true);
        hiloCalculo.start();
    }
}
