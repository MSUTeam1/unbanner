package unbanner;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfessorTest {

  private Professor bob;

  // Make sure we have the same object to test against for all tests
  @Before
  public void initObjects() {
    bob = new Professor("Bob", "Jones");
    bob.setId(new ObjectId("f240af3fe8bf240af3fe8ea1"));
  }

  @Test
  public void getIdTest() throws Exception {
    assertEquals("f240af3fe8bf240af3fe8ea1", bob.getId().toHexString());
  }

  @Test
  public void setIdTest() throws Exception {
    bob.setId(new ObjectId("f240af3fe8bf240af3fe8e33"));
    assertEquals("f240af3fe8bf240af3fe8e33", bob.getId().toHexString());
  }

  @Test
  public void toStringTest() throws Exception {
    assertEquals("Professor[id=f240af3fe8bf240af3fe8ea1, firstName='Bob', lastName='Jones']",
        bob.toString());
  }


  @Test
  public void getFirstName() throws Exception {
    assertEquals("Bob", bob.getFirstName());
  }

  @Test
  public void getLastName() throws Exception {
    assertEquals("Jones", bob.getLastName());
  }

  @Test
  public void setLastName() throws Exception {
    bob.setLastName("Smith");
    assertEquals("Smith", bob.getLastName());
  }

  @Test
  public void setFirstName() throws Exception {
    bob.setFirstName("Alice");
    assertEquals("Alice", bob.getFirstName());
  }


}