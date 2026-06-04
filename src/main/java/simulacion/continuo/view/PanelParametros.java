package simulacion.continuo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panel que organiza y gestiona la lista de controles de parámetros.
 */
public class PanelParametros extends GridPane {
    private final List<ControlParametro> controles = new ArrayList<>();
    private int filaActual = 0;

    public PanelParametros() {
        this.setHgap(10);
        this.setVgap(15);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.TOP_LEFT);
    }

    public void agregarControl(ControlParametro cp) {
        controles.add(cp);
        
        // Fila 1: Nombre y Valor
        this.add(cp.getLblNombre(), 0, filaActual);
        this.add(new HBox(cp.getLblValor(), cp.getLblUnidad()), 1, filaActual);
        
        // Fila 2: Slider expandido
        this.add(cp.getSlider(), 0, filaActual + 1, 2, 1);
        cp.getSlider().setMaxWidth(Double.MAX_VALUE);
        
        filaActual += 2;
    }

    public void limpiar() {
        controles.clear();
        this.getChildren().clear();
        filaActual = 0;
    }

    public Map<String, Double> obtenerValores() {
        Map<String, Double> valores = new HashMap<>();
        controles.forEach(c -> valores.put(c.getId(), c.getValor()));
        return valores;
    }

    public void setDeshabilitado(String id, boolean deshabilitado) {
        buscar(id).ifPresent(c -> c.setDeshabilitado(deshabilitado));
    }

    public void setValor(String id, double valor) {
        buscar(id).ifPresent(c -> c.setValor(valor));
    }

    private java.util.Optional<ControlParametro> buscar(String id) {
        return controles.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
