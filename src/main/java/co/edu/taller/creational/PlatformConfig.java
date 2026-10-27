package co.edu.taller.creational;

import java.util.Map;

/**
 * Singleton que comparte la configuracion predeterminada de la plataforma.
 */
public class PlatformConfig {
    // La inicializacion estatica crea una instancia unica y segura entre hilos.
    private static final PlatformConfig INSTANCE = new PlatformConfig();
    private final Map<String, String> values = Map.of("environment", "production");

    private PlatformConfig() {
    }

    public static PlatformConfig instance() {
        return INSTANCE;
    }

    public String get(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Configuration key must not be blank");
        }
        // No se devuelve null silenciosamente cuando la clave no esta configurada.
        String value = values.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Unknown configuration key: " + key);
        }
        return value;
    }
}