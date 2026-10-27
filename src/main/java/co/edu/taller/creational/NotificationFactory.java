package co.edu.taller.creational;

/**
 * Factory sencilla que selecciona un notificador Email o SMS segun el canal.
 */
public class NotificationFactory {
    public interface Notifier {
        String send(String recipient, String message);
    }

    public enum Channel {EMAIL, SMS}

    public Notifier create(Channel channel) {
        // Evita intentar crear un notificador sin un canal definido.
        if (channel == null) {
            throw new IllegalArgumentException("Channel is required");
        }

        // Cada canal produce un Notifier distinto, ocultando esa seleccion al cliente.
        return switch (channel) {
            case EMAIL -> (recipient, message) -> "EMAIL:" + recipient + ":" + message;
            case SMS -> (recipient, message) -> "SMS:" + recipient + ":" + message;
        };
    }
}