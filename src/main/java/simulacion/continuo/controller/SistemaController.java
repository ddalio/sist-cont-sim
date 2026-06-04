package simulacion.continuo.controller;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import simulacion.continuo.model.configuracion.Configuracion;
import simulacion.continuo.model.configuracion.ConfiguracionLoader;
import simulacion.continuo.model.configuracion.ParametroConfig;
import simulacion.continuo.model.SimuladorService;
import simulacion.continuo.view.SistemaGrafico;

/**
 * Controlador dinámico de la interfaz.  */
public class SistemaController {

    @FXML private LineChart<Number, Number> chartEvolucion;
    @FXML private LineChart<Number, Number> chartFases;
    @FXML private Label lblTiempo;
    @FXML private VBox contenedorParametros;
    @FXML private ToggleButton btnTipoSistema;
    @FXML private Button btnRun, btnLimpiar, btnValoresIniciales;

    private SistemaGrafico vista;
    private final SimuladorService simuladorService = new SimuladorService();
    private final Map<String, Slider> mapaSliders = new HashMap<>();

    @FXML
    public void initialize() {
        vista = new SistemaGrafico(chartEvolucion, chartFases, lblTiempo);
        generarInterfazDinamica();

        btnTipoSistema.selectedProperty().addListener((obs, oldVal, isSelected) -> {
            btnTipoSistema.setText(isSelected ? "Sistema: Lineal" : "Sistema: No Lineal");
            if (mapaSliders.containsKey("coeficienteNoLinealidad")) {
                mapaSliders.get("coeficienteNoLinealidad").setDisable(isSelected);
            }
        });
        
        vista.preparar();
    }

    private void generarInterfazDinamica() {
        Configuracion config = ConfiguracionLoader.load();
        contenedorParametros.getChildren().clear();
        mapaSliders.clear();

        for (Map.Entry<String, ParametroConfig> entrada : config.parametros().entrySet()) {
            String nombreParam = entrada.getKey();
            ParametroConfig param = entrada.getValue();

            VBox bloque = new VBox(5.0);
            
            HBox filaLabels = new HBox(5.0);
            filaLabels.setAlignment(Pos.CENTER_LEFT);
            
            Label lblNombre = new Label(param.label() + ": ");
            lblNombre.setTextFill(Color.BLACK);
            
            Label lblValor = new Label(String.format("%.2f", param.valor()));
            lblValor.setTextFill(Color.BLACK);
            
            Label lblUnidad = new Label(param.unidad());
            lblUnidad.setTextFill(Color.BLACK);
            
            filaLabels.getChildren().addAll(lblNombre, lblValor, lblUnidad);

            Slider slider = new Slider(param.min(), param.max(), param.valor());
            slider.setShowTickMarks(false);
            
            slider.valueProperty().addListener((obs, oldVal, newVal) -> 
                lblValor.setText(String.format("%.2f", newVal.doubleValue()))
            );

            mapaSliders.put(nombreParam, slider);
            bloque.getChildren().addAll(filaLabels, slider);
            contenedorParametros.getChildren().add(bloque);
        }
    }

    @FXML
    public void clickValoresIniciales() {
        Configuracion config = ConfiguracionLoader.load();
        for (Map.Entry<String, ParametroConfig> entrada : config.parametros().entrySet()) {
            Slider s = mapaSliders.get(entrada.getKey());
            if (s != null) s.setValue(entrada.getValue().valor());
        }
        btnTipoSistema.setSelected(false);
    }

    @FXML
    public void clickIniciar() {
        desactivarControles(true);
        vista.preparar();
        simuladorService.ejecutar(obtenerParametrosUI(), btnTipoSistema.isSelected(), (pos, vel, fase, tFinal) -> {
            vista.actualizar(pos, vel, fase, tFinal);
            desactivarControles(false);
        });
    }

    private Map<String, Double> obtenerParametrosUI() {
        Map<String, Double> p = new HashMap<>();
        mapaSliders.forEach((nombre, slider) -> p.put(nombre, slider.getValue()));
        return p;
    }

    private void desactivarControles(boolean desactivar) {
        btnRun.setDisable(desactivar);
        btnValoresIniciales.setDisable(desactivar);
        contenedorParametros.setDisable(desactivar);
    }

    @FXML
    public void clickLimpiar() {
        vista.preparar();
    }
}
