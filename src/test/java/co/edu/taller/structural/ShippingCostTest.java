package co.edu.taller.structural;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ShippingCostTest {
 @Test void standardCost(){assertEquals(12000,new ShippingCost.Standard().cost());}
 @Test void decoratorsCanBeCombined(){ShippingCost s=new ShippingCost.Priority(new ShippingCost.Insurance(new ShippingCost.Standard()));assertEquals(21500,s.cost());}
 @Test void rejectsNullDelegate(){assertThrows(IllegalArgumentException.class,()->new ShippingCost.Insurance(null));}
}