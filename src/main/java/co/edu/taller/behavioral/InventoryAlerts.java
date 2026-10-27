package co.edu.taller.behavioral;

import java.util.*;

/**
 * Observer que notifica a los listeners cuando el stock llega al umbral.
 */
public class InventoryAlerts {
    public interface Listener {
        void onLowStock(String sku, int quantity);
    }

    private final int threshold;
    private final List<Listener> listeners = new ArrayList<>();

    public InventoryAlerts(int threshold) {
        if (threshold <= 0) {
            throw new IllegalArgumentException("Threshold must be positive");
        }
        this.threshold = threshold;
    }

    public void subscribe(Listener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is required");
        }
        listeners.add(listener);
    }

    public void update(String sku, int quantity) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must not be negative");
        }
        if (quantity <= threshold) {
            // Se usa una copia para notificar sobre una lista estable de suscriptores.
            for (Listener listener : List.copyOf(listeners)) {
                listener.onLowStock(sku, quantity);
            }
        }
    }
}