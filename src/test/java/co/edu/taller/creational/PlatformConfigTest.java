package co.edu.taller.creational;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PlatformConfigTest {
 @Test void returnsSameInstance(){assertSame(PlatformConfig.instance(),PlatformConfig.instance());}
 @Test void exposesDefaultConfiguration(){assertEquals("production",PlatformConfig.instance().get("environment"));}
 @Test void rejectsBlankKey(){assertThrows(IllegalArgumentException.class,()->PlatformConfig.instance().get(" "));}
}