package unbanner;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.bson.types.ObjectId;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;


public class CourseTest {

  private Course course;
  private Course course2 = new Course();
  private Course course3 = new Course();
  private Section mockSection = Mockito.mock(Section.class);
  private Section mockSection2 = Mockito.mock(Section.class);
  private Section mockSection3 = Mockito.mock(Section.class);
  private Section mockSection4 = Mockito.mock(Section.class);
  private List<Section> sections = new ArrayList<Section>();
  private List<Section> sections2 = new ArrayList<Section>();
  private List<Course> fakeReqs;
  private List<Course> fakeReqs2 = new ArrayList<Course>();

  @Before
  public void init() {


    sections.add(mockSection);
    sections.add(mockSection2);
    sections2.add(mockSection3);
    sections2.add(mockSection4);
    course = new Course();
    course.setName("dummy name");
    course.setId(new ObjectId("507f191e810c19729de860ea"));
    course.setDepartment("dummy Department");
    course.setNumber(7777);
    course.setCredits(4);
    course.setDescription("dummy description");
    course.setObjectives("dummy objectives");
    course.setSections(sections);
    fakeReqs = new ArrayList<Course>();
    for (int i = 0; i < 5; i++) {
      Course filler = new Course();
      fakeReqs.add(filler);
    }
    fakeReqs2.add(course2);
    fakeReqs2.add(course3);
    course.setPrereqs(fakeReqs);
    course.setCoreqs(fakeReqs);
  }

  @Test
  public void getIdTest() {
    assertEquals(course.getId().toHexString(), "507f191e810c19729de860ea");
  }

  @Test
  public void setIdTest() {
    course.setId(new ObjectId("507f191e810c1972911860ea"));
    assertEquals(course.getId().toHexString(), "507f191e810c1972911860ea");
  }

  @Test
  public void getDepartment() {
    assertEquals(course.getDepartment(), "dummy Department");
  }

  @Test
  public void setDepartment() {
    course.setDepartment("hello");
    assertEquals(course.getDepartment(), "hello");
  }

  @Test
  public void setName() {
    course.setName("hello");
    assertEquals(course.getName(), "hello");
  }


  @Test
  public void getName() {
    assertEquals(course.getName(), "dummy name");
  }

  @Test
  public void getNumber() {
    assertEquals(course.getNumber(), 7777);
  }

  @Test
  public void setNumber() {
    course.setNumber(5555);
    assertEquals(course.getNumber(), 5555);
  }

  @Test
  public void getCredits() {
    assertEquals(course.getCredits(), 4);
  }

  @Test
  public void setCredits() {
    course.setCredits(99);
    assertEquals(course.getCredits(), 99);
  }

  @Test
  public void getDescription() {
    assertEquals(course.getDescription(), "dummy description");
  }

  @Test
  public void setDescriptin() {
    course.setDescription("hello");
    assertEquals(course.getDescription(), "hello");
  }

  @Test
  public void getObjectives() {
    assertEquals(course.getObjectives(), "dummy objectives");
  }

  @Test
  public void setObjectives() {
    course.setObjectives("hello");
    assertEquals(course.getObjectives(), "hello");
  }

  @Test
  public void getCoreqs() {
    assertEquals(course.getCoreqs(), fakeReqs);
  }

  @Test
  public void setCoreqs() {
    Assert.assertNotEquals(course.coreqs, fakeReqs2);
    course.setCoreqs(fakeReqs2);
    Assert.assertEquals(course.getCoreqs(), fakeReqs2);
  }

  @Test
  public void setPrereqs() {
    Assert.assertNotEquals(course.prereqs, fakeReqs2);
    course.setPrereqs(fakeReqs2);
    Assert.assertEquals(course.getPrereqs(), fakeReqs2);
  }

  @Test
  public void getPrereqs() {
    assertEquals(course.getPrereqs(), fakeReqs);
  }

  @Test
  public void setSections(){
    Assert.assertNotEquals(course.sections, sections2);
    course.setSections(sections2);
    Assert.assertEquals(course.sections, sections2);
  }

  @Test
  public void getSections(){
    Assert.assertEquals(course.sections, sections);
  }

}
