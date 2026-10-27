package co.edu.taller.behavioral;

/**
 * State que controla las transiciones permitidas del ciclo de solicitud.
 */
public class RequestLifecycle {
    public enum Status {RECEIVED, IN_REVIEW, APPROVED, REJECTED}

    private Status status = Status.RECEIVED;

    public Status status() {
        return status;
    }

    public void review() {
        transition(Status.RECEIVED, Status.IN_REVIEW);
    }

    public void approve() {
        transition(Status.IN_REVIEW, Status.APPROVED);
    }

    public void reject() {
        transition(Status.IN_REVIEW, Status.REJECTED);
    }

    private void transition(Status expected, Status next) {
        // Cada operacion solo se permite desde el estado indicado para ese flujo.
        if (status != expected) {
            throw new IllegalStateException("Cannot transition from " + status + " to " + next);
        }
        status = next;
    }
}