package simulacion.continuo.variables;

import java.util.List;
import java.util.Map;

public abstract class Variable {
    private String nombre;
    private double valor;
    private double derivada;

    public Variable(String nombre, double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDerivada() {
        return derivada;
    }

    public void setDerivada(double derivada) {
        this.derivada = derivada;
    }

    public void avanzar(double dt) {
        valor += derivada * dt;
    }

    public abstract void calcularDerivada(Map<String, Double> parametros, List<Variable> variables);
}