package unbanner;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CourseTest {
    private Course dummyCourse;
    @Before
    public void init() {
        dummyCourse = new Course();
        dummyCourse.setId("dummy ID");
        dummyCourse.setDepartment("dummy Department");
        dummyCourse.setNumber(7777);
        dummyCourse.setCredits(4);
        dummyCourse.setDescription("dummy description");
    }
    @Test
    public void getIdTest() {
        assertEquals(dummyCourse.getId(), "dummy ID");
    }
    @Test
    public void setIdTest() {
        dummyCourse.setId("different ID");
        assertEquals(dummyCourse.getId(),"different ID");
    }

    @Test
    public void getDepartment() {
        assertEquals(dummyCourse.getDepartment(), "dummy Department");
    }

    @Test
    public void getNumber() {
        assertEquals(dummyCourse.getNumber(), 7777);
    }

    @Test
    public void getCredits() {
        assertEquals(dummyCourse.getCredits(),4);
    }
    @Test
    public void getDescription() {
        assertEquals(dummyCourse.getDescription(),"dummy description");
    }
}
