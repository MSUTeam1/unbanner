package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestDataServiceImpl implements TestDataService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private SemesterRepository semesterRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  NineHundredRepository nineHundredRepository;

  @Autowired
  private NineHundredService nineHundredService;

  @Override
  public void loadTestData() {

    studentRepository.deleteAll();
    courseRepository.deleteAll();
    sectionRepository.deleteAll();
    buildingRepository.deleteAll();
    roomRepository.deleteAll();
    professorRepository.deleteAll();
    semesterRepository.deleteAll();
    nineHundredRepository.deleteAll();
    nineHundredRepository.save(new NineHundred(9000)); //start 900 number sequence at 9000

    // save a couple of Courses and Students
    courseRepository.save(new Course("Computer Science I", 1050,
        4, "CS", "Intro to Computer Science",
        "Learning basics of programming in computer science"));
    courseRepository.save(new Course("Computer Science II", 2050,
        4, "CS", "Computer Science 2",
        "Learning Object Oriented Programming in Computer Science"));
    studentRepository.save(new Student("Alice", "Smith"));
    studentRepository.save(new Student("Bob", "Smith"));
    sectionRepository.save(new Section(101, courseRepository.findByName("Computer Science I")));
    sectionRepository.save(new Section(201, courseRepository.findByName("Computer Science II")));
    sectionRepository.save(new Section(102, courseRepository.findByName("Computer Science I")));
    professorRepository.save(new Professor("Steve", "Beaty"));
    Semester semest1 = new Semester("Fall", 2017);
    Semester semest2 = new Semester("Spring", 2018);
    Semester semest3 = new Semester("Spring", 2019);
    semesterRepository.save(semest1);
    semesterRepository.save(semest2);
    semesterRepository.save(semest3);

    Student alice = studentRepository.findByFirstName("Alice");
    Student bob = studentRepository.findByFirstName("Bob");
    Course c1 = courseRepository.findByName("Computer Science I");
    Course c2 = courseRepository.findByName("Computer Science II");
    Section s1 = sectionRepository.findByCourse(c1).get(0);
    Section s2 = sectionRepository.findByCourse(c2).get(0);
    Section s3 = sectionRepository.findByCourse(c1).get(1);
    s1.addToSchedule(Weekday.M);
    s1.addToSchedule(Weekday.W);
    s3.addToSchedule(Weekday.T);
    s3.addToSchedule(Weekday.TH);
    s2.addToSchedule(Weekday.M);
    s2.addToSchedule(Weekday.W);


    Building bld1 = new Building("Building One", "This is 1st building");
    Building bld2 = new Building("Building Two", "This is a 2nd building");
    Building bld3 = new Building("Building Three", "This is a 3rd building");
    Room rm1 = new Room("room1001", 35);
    Room rm2 = new Room("room2000", 20);
    buildingRepository.save(bld1);
    buildingRepository.save(bld2);
    buildingRepository.save(bld3);
    roomRepository.save(rm1);
    roomRepository.save(rm2);
    s1.room = rm1;
    s2.room = rm2;
    s3.room = rm2;
    s1.addSectionToRoomList(rm1);
    s2.addSectionToRoomList(rm2);
    s3.addSectionToRoomList(rm2);
    sectionRepository.save(s1);
    sectionRepository.save(s2);
    sectionRepository.save(s3);
    bld1.rooms.add(rm1);
    bld1.rooms.add(rm2);

    rm1.building = bld1;
    rm2.building = bld1;


    alice.setSections((List<Section>) new ArrayList<Section>(Arrays.asList(s1, s2, s3)));
    bob.setSections((List<Section>) new ArrayList<Section>(Arrays.asList(s1, s2, s3)));

    alice.studentNum = nineHundredService.getNext();
    bob.studentNum = nineHundredService.getNext();

    s1.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(alice, bob)));
    s2.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob, alice)));
    s3.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob, alice)));

    c1.addSection(s1);
    c1.addSection(s3);
    c2.addSection(s2);

    semest1.sections.add(s1);
    semest2.sections.add(s2);
    semest3.sections.add(s3);
    semesterRepository.save(semest1);
    semesterRepository.save(semest2);
    semesterRepository.save(semest3);

    studentRepository.save(alice);
    studentRepository.save(bob);

    courseRepository.save(c1);
    courseRepository.save(c2);

    sectionRepository.save(s1);
    sectionRepository.save(s2);
    sectionRepository.save(s3);


    buildingRepository.save(bld1);

    roomRepository.save(rm1);
    roomRepository.save(rm2);

  }
}
