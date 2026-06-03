package simulacion.continuo.variables;
import java.util.Map;

public class ModeloNoLineal implements ModeloFisico {
    @Override
    public double calcularAceleracion(double x, double v, Map<String, Double> p) {
        
        double m = p.getOrDefault("masa", 1.0);
        double b = p.getOrDefault("coeficienteAmortiguamiento", 0.5);
        double k = p.getOrDefault("constanteElasticaLineal", 20.0);
        double alfa = p.getOrDefault("coeficienteNoLinealidad", 0.1);
        double g = p.getOrDefault("aceleracionGravitatoria", 9.8);

    
        double term1 = -(b / m) * Math.pow(Math.abs(v), 0.5) * v;
        double term2 = -(k / m) * x * (1 + alfa * x * x);
        
        return term1 + term2 - g;
    }
}