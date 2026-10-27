package co.edu.taller.structural;

/**
 * Adapter que convierte el importe interno en pesos al formato heredado en centavos.
 */
public class PaymentGatewayAdapter {
    public interface PaymentPort {
        String charge(String orderId, long pesos);
    }

    public static class LegacyGateway {
        public String chargeInCents(String id, long cents) {
            return "LEGACY:" + id + ":" + cents;
        }
    }

    private final LegacyGateway legacy;

    public PaymentGatewayAdapter(LegacyGateway legacy) {
        if (legacy == null) {
            throw new IllegalArgumentException("Legacy gateway is required");
        }
        this.legacy = legacy;
    }

    public String charge(String orderId, long pesos) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID must not be blank");
        }
        if (pesos <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        // La API heredada trabaja en centavos, por eso se adapta la unidad recibida en pesos.
        long cents = Math.multiplyExact(pesos, 100);
        return legacy.chargeInCents(orderId, cents);
    }
}