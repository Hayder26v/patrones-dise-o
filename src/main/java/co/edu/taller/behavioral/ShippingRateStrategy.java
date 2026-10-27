package co.edu.taller.behavioral;

/**
 * Strategy que selecciona el calculo de tarifa segun el modo de envio.
 */
public class ShippingRateStrategy {
    public interface Rate {
        long calculate(long base);
    }

    public enum Mode {STANDARD, EXPRESS, NIGHT}

    // Cada estrategia aplica un multiplicador distinto a la tarifa base.
    private static final Rate STANDARD = base -> base;
    private static final Rate EXPRESS = base -> Math.multiplyExact(base, 18) / 10;
    private static final Rate NIGHT = base -> Math.multiplyExact(base, 25) / 10;

    public long quote(Mode mode, long base) {
        if (mode == null) {
            throw new IllegalArgumentException("Shipping mode is required");
        }
        if (base <= 0) {
            throw new IllegalArgumentException("Base amount must be positive");
        }

        Rate rate = switch (mode) {
            case STANDARD -> STANDARD;
            case EXPRESS -> EXPRESS;
            case NIGHT -> NIGHT;
        };
        // La cotizacion delega el calculo en la estrategia seleccionada.
        return rate.calculate(base);
    }
}