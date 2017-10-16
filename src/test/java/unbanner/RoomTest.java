package unbanner;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admiral Fresh on 10/16/2017.
 */
public class RoomTest {

    Room room;

    @Before
    public void setUp() throws Exception {
        room = new Room("room name", 10);
        room.setId("testID");
    }


    @Test
    public void RoomTest() throws Exception{
        Room room2 = new Room();
        assertEquals(room2.name, "");
    }

    @Test
    public void getIdTest() throws Exception {
        assertEquals(room.getId(),"testID");

    }

    @Test
    public void setIdTest() throws Exception {
        room.setId("new id");
        assertEquals(room.getId(), "new id");
    }

    @Test
    public void getNameTest() throws Exception {
        assertEquals(room.getName(), "room name");
    }

    @Test
    public void setNameTest() throws Exception {
        room.setName("new name");
        assertEquals(room.getName(), "new name" );
    }

    @Test
    public void getSize() throws Exception {
        assertEquals(room.getSize(),10);
    }

    @Test
    public void setSize() throws Exception {
        room.setSize(15);
        assertEquals(room.getSize(), 15);

    }

}