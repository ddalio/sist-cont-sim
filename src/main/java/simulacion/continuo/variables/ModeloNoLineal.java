package simulacion.continuo.variables;
import java.util.Map;

public class ModeloNoLineal implements ModeloFisico {
    @Override
    public double calcularAceleracion(double x, double v, Map<String, Double> p) {
        // Usamos exactamente los nombres que vienen en tu JSON
        double m = p.getOrDefault("masa", 1.0);
        double b = p.getOrDefault("coeficienteAmortiguamiento", 0.5);
        double k = p.getOrDefault("constanteElasticaLineal", 20.0); // Nota el nombre
        double alfa = p.getOrDefault("coeficienteNoLinealidad", 0.1); // Nota el nombre
        double g = p.getOrDefault("aceleracionGravitatoria", 9.8);   // Nota el nombre

        // Cálculo ajustado con los valores del JSON
        double term1 = -(b / m) * Math.pow(Math.abs(v), 0.5) * v;
        double term2 = -(k / m) * x * (1 + alfa * x * x);
        
        return term1 + term2 - g;
    }
}