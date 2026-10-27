package co.edu.taller.structural;

/**
 * Facade que coordina la reserva de inventario y el cobro del checkout.
 */
public class CheckoutFacade {
    public interface Inventory {
        boolean reserve(String sku, int quantity);
    }

    public interface Payment {
        boolean charge(String orderId, long amount);
    }

    private final Inventory inventory;
    private final Payment payment;

    public CheckoutFacade(Inventory i, Payment p) {
        if (i == null || p == null) {
            throw new IllegalArgumentException("Inventory and payment services are required");
        }
        inventory = i;
        payment = p;
    }

    public String checkout(String orderId, String sku, int quantity, long amount) {
        // Se validan los datos antes de modificar inventario o intentar un cobro.
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID must not be blank");
        }
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!inventory.reserve(sku, quantity)) {
            throw new IllegalStateException("Inventory could not be reserved");
        }
        // Solo se confirma la orden despues de reservar inventario y cobrar correctamente.
        if (!payment.charge(orderId, amount)) {
            throw new IllegalStateException("Payment was declined");
        }
        return "CONFIRMED:" + orderId;
    }
}