package co.edu.taller.creational;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ExecutiveReportTest {
 @Test void buildsReportWithSections(){var r=new ExecutiveReport.Builder().title("Cloud Operations").period("Q3 2026").addSection("Incidents").addSection("Costs").build();assertEquals("Cloud Operations",r.title());assertEquals(2,r.sections().size());}
 @Test void requiresTitleAndPeriod(){assertThrows(IllegalStateException.class,()->new ExecutiveReport.Builder().title("Report").build());}
 @Test void sectionsAreImmutable(){var r=new ExecutiveReport.Builder().title("A").period("B").build();assertThrows(UnsupportedOperationException.class,()->r.sections().add("x"));}
}