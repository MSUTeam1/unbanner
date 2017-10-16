package unbanner;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class BuildingTest {
    Building bld1;

    @Before
    public void setUp() throws Exception {
        bld1 = new Building("Building One","This is 1st building");
        bld1.setId("testID");
        Room rm1 = new Room("room1 id", 30);
        bld1.rooms.add(rm1);

    }

    @Test
    public void BuidingTest() throws Exception{
        Building bld2 = new Building();
        assertEquals(bld2.description, "");
        assertEquals(bld2.name, "");
    }

    @Test
    public void getIdTest() throws Exception {
        assertEquals(bld1.getId(),"testID");

    }

    @Test
    public void setIdTest() throws Exception {
        bld1.setId("new id");
        assertEquals(bld1.getId(), "new id");
    }

    @Test
    public void getNameTest() throws Exception {
        assertEquals(bld1.getName(), "Building One");
    }

    @Test
    public void setNameTest() throws Exception {
        bld1.setName("new name");
        assertEquals(bld1.getName(), "new name" );
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
        assertEquals(bld1.toString(), "Building[id=testID, description='This is 1st building']");

    }

}