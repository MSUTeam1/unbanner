package unbanner;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;




public class BuildingTest {
  Building bld1;

  @Before
  public void setUp() throws Exception {
    bld1 = new Building("Building One", "This is 1st building");
    bld1.setId(new ObjectId("abcd1234abcdabcd1234abcd"));
    Room rm1 = new Room("room1 id", 30);
    System.out.println("_____________");
    System.out.println("_____________");
    System.out.println("_____________");
    System.out.println("_____________");
    System.out.println("_____________");
    System.out.println(bld1.toString());
    System.out.println(rm1.id);
    bld1.rooms.add(rm1);

  }

  @Test
  public void buidingTest() throws Exception {
    Building bld2 = new Building();
    assertEquals(bld2.description, "");
    assertEquals(bld2.name, "");
  }

  @Test
  public void getIdTest() throws Exception {
    assertEquals(bld1.getId().toHexString(), "abcd1234abcdabcd1234abcd");

  }

  @Test
  public void setIdTest() throws Exception {
    bld1.setId(new ObjectId("abcd1234abcdabcd12341111"));
    assertEquals(bld1.getId().toHexString(), "abcd1234abcdabcd12341111");
  }

  @Test
  public void getNameTest() throws Exception {
    assertEquals(bld1.getName(), "Building One");
  }

  @Test
  public void setNameTest() throws Exception {
    bld1.setName("new name");
    assertEquals(bld1.getName(), "new name");
  }

  @Test
  public void setDescriptionTest() throws Exception {
    bld1.setDescription("new desc");
    assertEquals(bld1.getDescription(), "new desc");
  }

  @Test
  public void getDescriptionTest() throws Exception {
    assertEquals(bld1.getDescription(), "This is 1st building");

  }

  @Test
  public void toStringTest() throws Exception {
//    bld1.rooms.get(0).setId(new ObjectId("12345"));

    assertEquals(bld1.toString(),
        "Building[name Building One\n" +
                "    " + "room room1 id" +
                "\nid abcd1234abcdabcd1234abcd]");
  }

}