package simulacion.continuo.model.configuracion;

/**
 * Representa la configuración y metadatos de un parámetro de simulación.
 */
public record ParametroConfig(
    double valor,
    double min,
    double max,
    String unidad,
    String label
) {}
