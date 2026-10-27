package co.edu.taller.structural;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CheckoutFacadeTest {
 @Test void completesCheckout(){var f=new CheckoutFacade((s,q)->true,(id,a)->true);assertEquals("CONFIRMED:O-1",f.checkout("O-1","SKU-2",2,50000));}
 @Test void stopsWhenInventoryUnavailable(){var f=new CheckoutFacade((s,q)->false,(id,a)->true);assertThrows(IllegalStateException.class,()->f.checkout("O-1","SKU-2",2,50000));}
 @Test void stopsWhenPaymentDeclined(){var f=new CheckoutFacade((s,q)->true,(id,a)->false);assertThrows(IllegalStateException.class,()->f.checkout("O-1","SKU-2",2,50000));}
}