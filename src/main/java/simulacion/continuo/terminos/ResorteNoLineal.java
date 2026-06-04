package simulacion.continuo.terminos;

import simulacion.continuo.Estado;

public class ResorteNoLineal implements Termino {

    private final double constanteElasticaLineal;
    private final double coeficienteNoLinealidad;
    private final double masa;

    public ResorteNoLineal(
            double constanteElasticaLineal,
            double coeficienteNoLinealidad,
            double masa) {
        this.constanteElasticaLineal = constanteElasticaLineal;
        this.coeficienteNoLinealidad = coeficienteNoLinealidad;
        this.masa = masa;
    }

    @Override
    public double calcular(Estado estado) {
        double posicion = estado.get("posicion");

        return -(constanteElasticaLineal
                * posicion
                * (1 + coeficienteNoLinealidad * posicion * posicion))
                / masa;
    }
}