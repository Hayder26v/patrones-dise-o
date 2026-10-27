package co.edu.taller.structural;

/**
 * Componente del Decorator para combinar costos base, seguro y prioridad.
 */
public interface ShippingCost {
    long cost();

    class Standard implements ShippingCost {
        @Override
        public long cost() {
            // Costo base al que los decoradores pueden agregar servicios.
            return 12000;
        }
    }

    class Insurance implements ShippingCost {
        private final ShippingCost delegate;

        public Insurance(ShippingCost delegate) {
            if (delegate == null) {
                throw new IllegalArgumentException("Shipping cost is required");
            }
            this.delegate = delegate;
        }

        @Override
        public long cost() {
            // Delega el costo base y suma el recargo del seguro.
            return Math.addExact(delegate.cost(), 6000);
        }
    }

    class Priority implements ShippingCost {
        private final ShippingCost delegate;

        public Priority(ShippingCost delegate) {
            if (delegate == null) {
                throw new IllegalArgumentException("Shipping cost is required");
            }
            this.delegate = delegate;
        }

        @Override
        public long cost() {
            // Delega el costo existente y suma el recargo por prioridad.
            return Math.addExact(delegate.cost(), 3500);
        }
    }
}
