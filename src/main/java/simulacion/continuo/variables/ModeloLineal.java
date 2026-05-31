
package simulacion.continuo.variables;
import java.util.Map;

public class ModeloLineal implements ModeloFisico {    
    
    @Override
    public double calcularAceleracion(double x, double v, Map<String, Double> p) {
        double m = p.get("masa");
        double b = p.get("coeficienteAmortiguamiento"); // Lineal
        double k = p.get("constanteElasticaLineal");
        return (-b * v - k * x) / m;
    }
}