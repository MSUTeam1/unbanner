package unbanner;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

  Room room;

  @Before
  public void setUp() throws Exception {
    room = new Room("room name", 10);
    room.setId(new ObjectId("abcd1234abcdabcd1234abcd"));
  }


  @Test
  public void roomTest() throws Exception {
    Room room2 = new Room();
    assertEquals(room2.name, "");
  }

  @Test
  public void getIdTest() throws Exception {
    assertEquals(room.getId().toHexString(), "abcd1234abcdabcd1234abcd");

  }

  @Test
  public void setIdTest() throws Exception {
    room.setId(new ObjectId("abcd1234abcdabcd1234ffff"));
    assertEquals(room.getId().toHexString(), "abcd1234abcdabcd1234ffff");
  }

  @Test
  public void getNameTest() throws Exception {
    assertEquals(room.getName(), "room name");
  }

  @Test
  public void setNameTest() throws Exception {
    room.setName("new name");
    assertEquals(room.getName(), "new name");
  }

  @Test
  public void getSize() throws Exception {
    assertEquals(room.getSize(), 10);
  }

  @Test
  public void setSize() throws Exception {
    room.setSize(15);
    assertEquals(room.getSize(), 15);

  }

}