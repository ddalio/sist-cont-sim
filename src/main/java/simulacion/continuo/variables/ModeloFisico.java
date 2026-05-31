package simulacion.continuo.variables;
import java.util.Map;

public interface ModeloFisico {
    double calcularAceleracion(double x, double v, Map<String, Double> parametros);
}