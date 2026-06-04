package simulacion.continuo.terminos;

import java.util.Map;
public class TerminoFactory {

    private final Map<String, Double> parametros;

    public TerminoFactory(Map<String, Double> parametros) {
        this.parametros = parametros;
    }

    public Termino crear(String nombre) {

        return switch (nombre) {

            case "Amortiguamiento" -> new Amortiguamiento(
                    parametros.get("coeficienteAmortiguamiento"),
                    parametros.get("masa")
            );

            case "ResorteNoLineal" -> new ResorteNoLineal(
                    parametros.get("constanteElasticaLineal"),
                    parametros.get("coeficienteNoLinealidad"),
                    parametros.get("masa")
            );

            case "Gravedad" -> new Gravedad(
                    parametros.get("aceleracionGravitatoria")
            );

            case "VelocidadActual" -> new VelocidadActual();

            default -> throw new IllegalArgumentException(
                    "Término desconocido: " + nombre
            );
        };
    }
}