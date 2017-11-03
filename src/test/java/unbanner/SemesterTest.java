package unbanner;

import org.junit.Assert;
import org.junit.Test;

public class SemesterTest {
  @Test
  public void toStringTest() {
    Assert.assertTrue(Semester.FALL.toString().equals("FALL"));
    Assert.assertTrue(Semester.SPRING.toString().equals("SPRING"));
    Assert.assertTrue(Semester.SUMMER.toString().equals("SUMMER"));
  }
}
