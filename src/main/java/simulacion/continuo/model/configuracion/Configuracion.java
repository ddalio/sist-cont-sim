package simulacion.continuo.model.configuracion;

import java.util.Map;

public record Configuracion(
    Map<String, ParametroConfig> parametros,
    double h,
    double t_max,
    double tolerance
) {}
