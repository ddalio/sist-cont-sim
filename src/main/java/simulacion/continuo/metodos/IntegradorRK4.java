package simulacion.continuo.metodos;

import simulacion.continuo.Estado;
import simulacion.continuo.Variable;
import java.util.HashMap;
import java.util.Map;

public class IntegradorRK4 implements Integrador {

    @Override
    public void paso(Estado estado, double h) {
        Map<String, Double> valoresIniciales = obtenerValoresActuales(estado);

        Map<String, Double> k1 = calcularK(estado, h, 0, null, valoresIniciales);
        Map<String, Double> k2 = calcularK(estado, h, 0.5, k1, valoresIniciales);
        Map<String, Double> k3 = calcularK(estado, h, 0.5, k2, valoresIniciales);
        Map<String, Double> k4 = calcularK(estado, h, 1.0, k3, valoresIniciales);

        aplicarActualizacionFinal(estado, h, valoresIniciales, k1, k2, k3, k4);
    }

    private Map<String, Double> obtenerValoresActuales(Estado estado) {
        Map<String, Double> valores = new HashMap<>();
        for (Variable v : estado.getVariables()) {
            valores.put(v.getNombre(), v.getValor());
        }
        return valores;
    }

    private Map<String, Double> calcularK(Estado estado, double h, double fraccionH, 
                                        Map<String, Double> kAnterior, 
                                        Map<String, Double> valoresIniciales) {
        
        for (Variable v : estado.getVariables()) {
            String nombre = v.getNombre();
            double desplazamiento = (kAnterior == null) ? 0 : fraccionH * h * kAnterior.get(nombre);
            v.setValor(valoresIniciales.get(nombre) + desplazamiento);
        }

        Map<String, Double> kActual = new HashMap<>();
        for (Variable v : estado.getVariables()) {
            v.calcularDerivada(estado);
            kActual.put(v.getNombre(), v.getDerivada());
        }
        return kActual;
    }

    private void aplicarActualizacionFinal(Estado estado, double h, 
                                          Map<String, Double> v0,
                                          Map<String, Double> k1, Map<String, Double> k2, 
                                          Map<String, Double> k3, Map<String, Double> k4) {
        for (Variable v : estado.getVariables()) {
            String id = v.getNombre();
            // Fórmula RK4: y(n+1) = y(n) + (h/6)(k1 + 2k2 + 2k3 + k4)
            double delta = (h / 6.0) * (k1.get(id) + 2 * k2.get(id) + 2 * k3.get(id) + k4.get(id));
            v.setValor(v0.get(id) + delta);
        }
    }
}
