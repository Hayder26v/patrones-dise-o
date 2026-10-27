package co.edu.taller.structural;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PaymentGatewayAdapterTest {
 @Test void convertsPesosToCents(){var a=new PaymentGatewayAdapter(new PaymentGatewayAdapter.LegacyGateway());assertEquals("LEGACY:ORD-7:125000",a.charge("ORD-7",1250));}
 @Test void rejectsNonPositiveAmount(){var a=new PaymentGatewayAdapter(new PaymentGatewayAdapter.LegacyGateway());assertThrows(IllegalArgumentException.class,()->a.charge("ORD-7",0));}
 @Test void rejectsMissingOrder(){var a=new PaymentGatewayAdapter(new PaymentGatewayAdapter.LegacyGateway());assertThrows(IllegalArgumentException.class,()->a.charge(" ",100));}
}