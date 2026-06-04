package simulacion.continuo.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import simulacion.continuo.model.configuracion.Configuracion;
import simulacion.continuo.model.configuracion.ConfiguracionLoader;
import simulacion.continuo.model.SimuladorService;
import simulacion.continuo.view.SistemaGrafico;
import simulacion.continuo.view.ControlParametro;
import simulacion.continuo.view.PanelParametros;


public class SistemaController {

    @FXML private LineChart<Number, Number> chartEvolucion;
    @FXML private LineChart<Number, Number> chartFases;
    @FXML private Label lblTiempo;
    @FXML private VBox contenedorParametros;
    @FXML private ToggleButton btnTipoSistema;
    @FXML private Button btnRun, btnLimpiar, btnValoresIniciales;

    private SistemaGrafico vista;
    private final SimuladorService simuladorService = new SimuladorService();
    private PanelParametros panelUI;
    private Configuracion config;

    @FXML
    public void initialize() {
        config = ConfiguracionLoader.load();
        vista = new SistemaGrafico(chartEvolucion, chartFases, lblTiempo);
        configurarPanelDinamico();

        btnTipoSistema.selectedProperty().addListener((obs, oldVal, isSelected) -> {
            btnTipoSistema.setText(isSelected ? "Sistema: Lineal" : "Sistema: No Lineal");
            panelUI.setDeshabilitado("coeficienteNoLinealidad", isSelected);
        });
        
        vista.preparar();
    }
    
    // crea el panel, y agrega un componente por cada parametro
    private void configurarPanelDinamico() {
        panelUI = new PanelParametros();
        contenedorParametros.getChildren().add(panelUI);

        config.parametros().forEach((id, param) -> {
            panelUI.agregarControl(new ControlParametro(id, param));
        });
    }

    @FXML
    public void clickValoresIniciales() {
        config.parametros().forEach((id, param) -> {
            panelUI.setValor(id, param.valor());
        });
        btnTipoSistema.setSelected(false);
    }

    @FXML
    public void clickIniciar() {
        desactivarControles(true);
        vista.preparar();
        simuladorService.ejecutar(panelUI.obtenerValores(), btnTipoSistema.isSelected(), (pos, vel, fase, tFinal) -> {
            vista.actualizar(pos, vel, fase, tFinal);
            desactivarControles(false);
        });
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
