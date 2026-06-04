package simulacion.continuo;

import simulacion.continuo.terminos.Termino;

import java.util.List;

public class Variable {
    private List<Termino> terminos;
    private String nombre;
    private double valor;
    private double derivada;

    public Variable(String nombre, double valor, List<Termino> terminos) {
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

    public void calcularDerivada(Estado estado) {
        double derivada = 0.0;
        for(Termino termino : terminos){
            derivada += termino.calcular(estado);
        }
        setDerivada(derivada);
    }
}