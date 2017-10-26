package unbanner;

import org.junit.Assert;
import org.junit.Test;


public class WeekdayTest {

  @Test
  public void toStringShouldWork() {
    Assert.assertTrue(Weekday.W.toString().equals("W"));
  }
}
