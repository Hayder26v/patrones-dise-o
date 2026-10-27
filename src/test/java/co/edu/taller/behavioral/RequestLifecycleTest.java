package co.edu.taller.behavioral;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class RequestLifecycleTest {
 @Test void followsValidApprovalFlow(){var r=new RequestLifecycle();r.review();r.approve();assertEquals(RequestLifecycle.Status.APPROVED,r.status());}
 @Test void followsValidRejectionFlow(){var r=new RequestLifecycle();r.review();r.reject();assertEquals(RequestLifecycle.Status.REJECTED,r.status());}
 @Test void rejectsInvalidTransition(){var r=new RequestLifecycle();assertThrows(IllegalStateException.class,r::approve);}
}