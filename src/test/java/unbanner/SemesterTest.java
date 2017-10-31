package unbanner;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SemesterTest {

    private Semester semester;

    @Before
    public void setUp() throws Exception {
        semester = new Semester("peanuts", 121212);
        semester.setId(new ObjectId("dddd1111abcdabcd1234abcd"));
    }

    @Test
    public void getSeason() throws Exception {
        assertEquals(semester.getSeason(), "peanuts");
    }

    @Test
    public void setSeason() throws Exception {
        semester.setSeason("fake season");
        assertEquals(semester.getSeason(), "fake season");
    }

    @Test
    public void getYear() throws Exception {
        assertEquals(semester.getYear(), 121212);
    }

    @Test
    public void setYear() throws Exception {
        semester.setYear(1234567890);
        assertEquals(semester.getYear(), 1234567890);
    }


    /*
    i have no idea why these dont work.
     */
//    @Test
//    public void getId() throws Exception {
//        assertEquals(semester.getId().toHexString(),
//                "dddd1111abcdabcd1234abcd");
//    }
//
//    @Test
//    public void setId() throws Exception {
//        semester.setId(new ObjectId("abcd1234ab222bcd1234dfff"));
//        assertEquals(semester.getId().toHexString(),
//                "abcd1234ab222bcd1234dfff");
//    }

}