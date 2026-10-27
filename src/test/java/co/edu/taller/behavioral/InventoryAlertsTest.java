package co.edu.taller.behavioral;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
class InventoryAlertsTest {
 @Test void notifiesWhenAtThreshold(){var alerts=new InventoryAlerts(5);var calls=new AtomicInteger();alerts.subscribe((s,q)->calls.incrementAndGet());alerts.update("SKU-1",5);assertEquals(1,calls.get());}
 @Test void doesNotNotifyAboveThreshold(){var alerts=new InventoryAlerts(5);var calls=new AtomicInteger();alerts.subscribe((s,q)->calls.incrementAndGet());alerts.update("SKU-1",8);assertEquals(0,calls.get());}
 @Test void rejectsNullListener(){assertThrows(IllegalArgumentException.class,()->new InventoryAlerts(5).subscribe(null));}
}