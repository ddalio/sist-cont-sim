package simulacion.continuo.view;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import simulacion.continuo.model.configuracion.ParametroConfig;

public class ControlParametro {
    private final String id;
    private final Label lblNombre;
    private final Label lblValor;
    private final Label lblUnidad;
    private final Slider slider;

    public ControlParametro(String id, ParametroConfig config) {
        this.id = id;
        
        this.lblNombre = new Label(config.label() + ": ");
        this.lblNombre.setTextFill(Color.BLACK);

        this.lblValor = new Label(String.format("%.2f", config.valor()));
        this.lblValor.setTextFill(Color.BLACK);
        this.lblValor.setStyle("-fx-font-weight: bold;");

        this.lblUnidad = new Label(" " + config.unidad());
        this.lblUnidad.setTextFill(Color.DARKGRAY);

        this.slider = new Slider(config.min(), config.max(), config.valor());
        this.slider.valueProperty().addListener((obs, oldVal, newVal) -> 
            lblValor.setText(String.format("%.2f", newVal.doubleValue()))
        );
    }

    public String getId() { return id; }
    public double getValor() { return slider.getValue(); }
    public void setValor(double valor) { slider.setValue(valor); }
    public void setDeshabilitado(boolean deshabilitado) { slider.setDisable(deshabilitado); }

    public Label getLblNombre() { return lblNombre; }
    public Label getLblValor() { return lblValor; }
    public Label getLblUnidad() { return lblUnidad; }
    public Slider getSlider() { return slider; }
}
