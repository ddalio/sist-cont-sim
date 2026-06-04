package simulacion.continuo.view;

import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * Encapsula la gestión de los gráficos y etiquetas de tiempo en la interfaz.
 * Su responsabilidad es puramente visual: inicializar series y actualizar puntos.
 */
public class SistemaGrafico {

    private final LineChart<Number, Number> chartEvolucion;
    private final LineChart<Number, Number> chartFases;
    private final Label lblTiempo;

    private XYChart.Series<Number, Number> seriePosicion;
    private XYChart.Series<Number, Number> serieVelocidad;
    private XYChart.Series<Number, Number> serieFaseEspacio;

    public SistemaGrafico(LineChart<Number, Number> chartEvolucion, LineChart<Number, Number> chartFases, Label lblTiempo) {
        this.chartEvolucion = chartEvolucion;
        this.chartFases = chartFases;
        this.lblTiempo = lblTiempo;
    }

    public void preparar() {
        chartEvolucion.getData().clear();
        chartFases.getData().clear();

        seriePosicion = new XYChart.Series<>();
        seriePosicion.setName("Posición (x)");
        serieVelocidad = new XYChart.Series<>();
        serieVelocidad.setName("Velocidad (v)");
        serieFaseEspacio = new XYChart.Series<>();
        serieFaseEspacio.setName("Trayectoria de Fase");

        chartEvolucion.getData().addAll(seriePosicion, serieVelocidad);
        chartFases.getData().add(serieFaseEspacio);

        lblTiempo.setText("Tiempo: 0.00 s");
    }

    public void actualizar(List<XYChart.Data<Number, Number>> posicion, 
                          List<XYChart.Data<Number, Number>> velocidad, 
                          List<XYChart.Data<Number, Number>> fases, 
                          double tiempoFinal) {
        
        seriePosicion.getData().setAll(posicion);
        serieVelocidad.getData().setAll(velocidad);
        serieFaseEspacio.getData().setAll(fases);
        
        lblTiempo.setText(String.format("Tiempo: %.2f s (Simulación Completada)", tiempoFinal));
    }

    public void mostrarTiempo(double t) {
        lblTiempo.setText(String.format("Tiempo: %.2f s", t));
    }
}
