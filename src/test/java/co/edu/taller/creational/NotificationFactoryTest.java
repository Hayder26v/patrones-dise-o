package co.edu.taller.creational;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class NotificationFactoryTest {
 @Test void createsEmailNotifier(){ assertEquals("EMAIL:a@company.com:Ready",new NotificationFactory().create(NotificationFactory.Channel.EMAIL).send("a@company.com","Ready")); }
 @Test void createsSmsNotifier(){ assertEquals("SMS:+573001112233:Ready",new NotificationFactory().create(NotificationFactory.Channel.SMS).send("+573001112233","Ready")); }
 @Test void rejectsMissingChannel(){ assertThrows(IllegalArgumentException.class,()->new NotificationFactory().create(null)); }
}