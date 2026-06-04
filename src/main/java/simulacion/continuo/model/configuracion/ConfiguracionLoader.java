package simulacion.continuo.model.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfiguracionLoader {
    private static final String CONFIG_FILE = "/config.json";
    private static Configuracion instance;

    public static Configuracion load() {
        if (instance != null) {
            return instance;
        }

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = ConfiguracionLoader.class.getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new RuntimeException("No se pudo encontrar el archivo " + CONFIG_FILE);
            }
            
            ConfiguracionDTO dto = mapper.readValue(is, ConfiguracionDTO.class);
            Map<String, ParametroConfig> simulationParams = new LinkedHashMap<>();
            
            if (dto.parametros != null) {
                for (Map<String, Object> item : dto.parametros) {
                    String nombre = (String) item.get("nombre");
                    double valor = ((Number) item.get("valor")).doubleValue();
                    double min = item.containsKey("min") ? ((Number) item.get("min")).doubleValue() : 0.0;
                    double max = item.containsKey("max") ? ((Number) item.get("max")).doubleValue() : Math.max(100.0, valor * 2);
                    String unidad = (String) item.getOrDefault("unidad", "");
                    String label = (String) item.getOrDefault("label", formatearNombre(nombre));
                    
                    simulationParams.put(nombre, new ParametroConfig(valor, min, max, unidad, label));
                }
            }
            instance = new Configuracion(simulationParams, dto.h, dto.t_max, dto.tolerance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la configuracion", e);
        }
    }

    private static String formatearNombre(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) return "";
        String result = camelCase.replaceAll("([A-Z])", " $1");
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    private static class ConfiguracionDTO {
        public double h = 0.001;
        public double t_max = 50.0;
        public double tolerance = 0.001;
        public List<Map<String, Object>> parametros;
    }
}
