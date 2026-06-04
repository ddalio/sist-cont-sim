package simulacion.continuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import simulacion.continuo.terminos.Termino;
import simulacion.continuo.terminos.TerminoFactory;

public class SistemaController {

    @FXML private LineChart<Number, Number> chartEvolucion;
    @FXML private LineChart<Number, Number> chartFases;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private NumberAxis fasesXAxis;
    @FXML private NumberAxis fasesYAxis;
    
    @FXML private Slider sliderM, sliderB, sliderK, sliderAlpha, sliderG;
    @FXML private Label labelM, labelB, labelK, labelAlpha, labelG, lblTiempo;
    @FXML private ToggleButton btnTipoSistema;
    @FXML private Button btnRun, btnLimpiar, btnValoresIniciales;

    private SistemaContinuo sistema;
    private final double PASO_INTEGRACION_H = 0.001; 
    private final double TIEMPO_MAXIMO = 50.0;

    private XYChart.Series<Number, Number> seriePosicion;
    private XYChart.Series<Number, Number> serieVelocidad;
    private XYChart.Series<Number, Number> serieFaseEspacio;

    @FXML
    public void initialize() {
        // Enlazar los textos dinámicos con los valores de los Sliders
        sliderM.valueProperty().addListener((obs, oldVal, newVal) -> labelM.setText(String.format("%.2f kg", newVal)));
        sliderB.valueProperty().addListener((obs, oldVal, newVal) -> labelB.setText(String.format("%.2f", newVal)));
        sliderK.valueProperty().addListener((obs, oldVal, newVal) -> labelK.setText(String.format("%.2f N/m", newVal)));
        sliderAlpha.valueProperty().addListener((obs, oldVal, newVal) -> labelAlpha.setText(String.format("%.2f", newVal)));
        sliderG.valueProperty().addListener((obs, oldVal, newVal) -> labelG.setText(String.format("%.2f m/s²", newVal)));

        // Cambiar dinámicamente el comportamiento estético del ToggleButton
        btnTipoSistema.selectedProperty().addListener((obs, oldVal, isSelected) -> {
            if (isSelected) {
                btnTipoSistema.setText("Sistema: Lineal");
                sliderAlpha.setDisable(true); // En lineal alpha no actúa
            } else {
                btnTipoSistema.setText("Sistema: No Lineal");
                sliderAlpha.setDisable(false);
            }
        });

        prepararSimulador();
    }

    private void prepararSimulador() {
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

    @FXML
    public void clickValoresIniciales() {
        sliderM.setValue(1.0);
        sliderB.setValue(0.5);
        sliderK.setValue(20.0);
        sliderAlpha.setValue(0.1);
        sliderG.setValue(9.8);
        btnTipoSistema.setSelected(false);
    }

    // Este es el método mapeado por el FXML como onAction="#clickIniciar"
    @FXML
    public void clickIniciar() {
        btnRun.setDisable(true);
        btnValoresIniciales.setDisable(true);
        prepararSimulador();

        // 1. Recopilar parámetros actuales de la UI
        Map<String, Double> parametros = new HashMap<>();
        
        parametros.put("masa", sliderM.getValue());
        parametros.put("coeficienteAmortiguamiento", sliderB.getValue());
        parametros.put("constanteElasticaLineal", sliderK.getValue());
        parametros.put("coeficienteNoLinealidad", sliderAlpha.getValue());
        parametros.put("aceleracionGravitatoria", sliderG.getValue());

        
        parametros.put("lineal", btnTipoSistema.isSelected() ? 1.0 : 0.0);

        // 2. Instanciar la Factoría de Términos
        TerminoFactory terminoFactory = new TerminoFactory(parametros);

        // Construir listas de términos condicionalmente basado en la selección del tipo de sistema
        List<Termino> terminosVelocidad;
        if (btnTipoSistema.isSelected()) {
            // Caso Lineal clásico
            terminosVelocidad = List.of(
                terminoFactory.crear("Gravedad"), 
                terminoFactory.crear("Amortiguamiento"), 
                terminoFactory.crear("ResorteLineal") 
            );
        } else {
            // Caso No Lineal original
            terminosVelocidad = List.of(
                terminoFactory.crear("Gravedad"), 
                terminoFactory.crear("Amortiguamiento"), 
                terminoFactory.crear("ResorteNoLineal")
            );
        }
        List<Termino> terminosPosicion = List.of(terminoFactory.crear("VelocidadActual"));

        // 3. Crear variables con sus condiciones iniciales definidas por el enunciado (x0=1.0, v0=0.0)
        Variable velocidad = new Variable("velocidad", 0.0, terminosVelocidad);
        Variable posicion = new Variable("posicion", 1.0, terminosPosicion);

        // 4. Armar el mapa de variables e inicializar el Estado
        Map<String, Variable> mapaVariables = new HashMap<>();
        mapaVariables.put(velocidad.getNombre(), velocidad);
        mapaVariables.put(posicion.getNombre(), posicion);
        Estado estado = new Estado(mapaVariables);

        // 5. Instanciar el backend del SistemaContinuo
        sistema = new SistemaContinuo(estado);
        
        // ¡Paso crucial! Llenamos la lista interna para evitar que el log/cálculo esté vacío
        sistema.agregarVariable(velocidad);
        sistema.agregarVariable(posicion);

        // Hilo en segundo plano para procesar la integración numérica sin congelar la GUI
        Thread hiloCalculo = new Thread(() -> {
            double t = 0.0;
            List<XYChart.Data<Number, Number>> listaPosicion = new ArrayList<>();
            List<XYChart.Data<Number, Number>> listaVelocidad = new ArrayList<>();
            List<XYChart.Data<Number, Number>> listaFases = new ArrayList<>();

            while (t < TIEMPO_MAXIMO) {
                sistema.paso();
                t = sistema.getTiempo();

                double currentX = posicion.getValor();
                double currentV = velocidad.getValor();

                // Muestreo cada 10 iteraciones (reducción de carga gráfica)
                if (Math.round(t / PASO_INTEGRACION_H) % 10 == 0) {
                    listaPosicion.add(new XYChart.Data<>(t, currentX));
                    listaVelocidad.add(new XYChart.Data<>(t, currentV));
                    listaFases.add(new XYChart.Data<>(currentX, currentV));
                }
            }

            final double tiempoFinal = t;
            Platform.runLater(() -> {
                seriePosicion.getData().setAll(listaPosicion);
                serieVelocidad.getData().setAll(listaVelocidad);
                serieFaseEspacio.getData().setAll(listaFases);
                
                lblTiempo.setText(String.format("Tiempo: %.2f s (Simulación Completada)", tiempoFinal));
                btnRun.setDisable(false);
                btnValoresIniciales.setDisable(false);
            });
        });

        hiloCalculo.setDaemon(true);
        hiloCalculo.start();
    }

    @FXML
    public void clickLimpiar() {
        prepararSimulador();
    }
}