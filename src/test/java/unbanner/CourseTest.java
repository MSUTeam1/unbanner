package unbanner;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

public class CourseTest {
    private Course dummyCourse;
    private List<Course> fakeReqs;
    @Before
    public void init() {
        dummyCourse = new Course();
        dummyCourse.setName("dummy name");
        dummyCourse.setId("dummy ID");
        dummyCourse.setDepartment("dummy Department");
        dummyCourse.setNumber(7777);
        dummyCourse.setCredits(4);
        dummyCourse.setDescription("dummy description");
        dummyCourse.setObjectives("dummy objectives");
        fakeReqs = new ArrayList<Course>();
        for (int i = 0; i < 5; i++) {
            Course filler = new Course();
            fakeReqs.add(filler);
        }
        dummyCourse.setPrereqs(fakeReqs);
        dummyCourse.setCoreqs(fakeReqs);
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
    public void setDepartment() {
        dummyCourse.setDepartment("hello");
        assertEquals(dummyCourse.getDepartment(),"hello");
    }

    @Test
    public void setName() {
        dummyCourse.setName("hello");
        assertEquals(dummyCourse.getName(),"hello");
    }


    @Test
    public void getName() {
        assertEquals(dummyCourse.getName(),"dummy name");
    }
    @Test
    public void getNumber() {
        assertEquals(dummyCourse.getNumber(), 7777);
    }

    @Test
    public void setNumber() {
        dummyCourse.setNumber(5555);
        assertEquals(dummyCourse.getNumber(),5555);
    }
    @Test
    public void getCredits() {
        assertEquals(dummyCourse.getCredits(),4);
    }

    @Test
    public void setCredits() {
        dummyCourse.setCredits(99);
        assertEquals(dummyCourse.getCredits(),99);
    }
    @Test
    public void getDescription() {
        assertEquals(dummyCourse.getDescription(),"dummy description");
    }
    @Test
    public void setDescriptin() {
        dummyCourse.setDescription("hello");
        assertEquals(dummyCourse.getDescription(), "hello");
    }
    @Test
    public void getObjectives() {
        assertEquals(dummyCourse.getObjectives(), "dummy objectives");
    }
    @Test
    public void setObjectives() {
        dummyCourse.setObjectives("hello");
        assertEquals(dummyCourse.getObjectives(),"hello");
    }
    @Test
    public void getCoreqs() {
        assertEquals(dummyCourse.getCoreqs(), fakeReqs);
    }
    @Test
    public void setCoreqs() {
        dummyCourse.setCoreqs(new ArrayList<Course>());
        assertNotSame(dummyCourse.getCoreqs(), fakeReqs);
    }

    @Test
    public void setPrereqs() {
        dummyCourse.setPrereqs(new ArrayList<Course>());
        assertNotSame(dummyCourse.getPrereqs(), fakeReqs);
    }

    @Test
    public void getPrereqs() {
        assertEquals(dummyCourse.getPrereqs(),fakeReqs);
    }
}
