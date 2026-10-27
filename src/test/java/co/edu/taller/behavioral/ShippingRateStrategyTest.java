package co.edu.taller.behavioral;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ShippingRateStrategyTest {
 @Test void calculatesStandard(){assertEquals(10000,new ShippingRateStrategy().quote(ShippingRateStrategy.Mode.STANDARD,10000));}
 @Test void calculatesExpress(){assertEquals(18000,new ShippingRateStrategy().quote(ShippingRateStrategy.Mode.EXPRESS,10000));}
 @Test void rejectsInvalidBase(){assertThrows(IllegalArgumentException.class,()->new ShippingRateStrategy().quote(ShippingRateStrategy.Mode.NIGHT,0));}
}