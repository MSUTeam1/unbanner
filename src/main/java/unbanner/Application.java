package unbanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  @Autowired
  private Globals globals;

  @Bean
  public Globals getGlobals(@Value("${global.school}") String school,
                            @Value("${global.freshman}") String freshman,
                            @Value("${global.sophomore}") String sophomore,
                            @Value("${global.junior}") String junior,
                            @Value("${global.senior}") String senior) {
    return new Globals() {

      @Override
      public String getSchool() {
        return school;
      }

      public String getFreshman() {
        return freshman;
      }

      public String getSophomore() {
        return sophomore;
      }

      public String getJunior() {
        return junior;
      }

      public String getSenior() {
        return senior;
      }
    };
  }

  @Bean
  public TemplateResolver templateResolver() {
    TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setCacheable(false);
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML5");
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.addDialect(new LayoutDialect());
    return templateEngine;
  }


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    studentRepository.deleteAll();
    courseRepository.deleteAll();
    sectionRepository.deleteAll();
    buildingRepository.deleteAll();
    roomRepository.deleteAll();


    // save a couple of Courses and Students
    courseRepository.save(new Course("Computer Science I", 1050,
        4, "CS", "Intro to Computer Science",
        "Learning basics of programming in computer science"));
    courseRepository.save(new Course("Computer Science II", 2050,
        4, "CS", "Computer Science 2",
        "Learning Object Oriented Programming in Computer Science"));
    studentRepository.save(new Student("Alice", "Smith", 900123456));
    studentRepository.save(new Student("Bob", "Smith", 900123456));
    sectionRepository.save(new Section(101, courseRepository.findByName("Computer Science I")));
    sectionRepository.save(new Section(201, courseRepository.findByName("Computer Science II")));
    sectionRepository.save(new Section(102, courseRepository.findByName("Computer Science I")));

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



    Building bld1 = new Building("Building One","This is 1st building");
    Building bld2 = new Building("Building Two","This is a 2nd building");
    Building bld3 = new Building("Building Three","This is a 3rd building");
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
    bld1.rooms.add(rm1);
    bld1.rooms.add(rm2);
    rm1.building = bld1;


    alice.setSections((List<Section>) new ArrayList<Section>(Arrays.asList(s1, s2, s3)));
    bob.setSections((List<Section>) new ArrayList<Section>(Arrays.asList(s1, s2, s3)));

    s1.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(alice, bob)));
    s2.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob, alice)));
    s3.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob, alice)));

    c1.addSection(s1);
    c1.addSection(s3);
    c2.addSection(s2);

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


    // fetch all Students
    System.out.println("Students found with findAll():");
    System.out.println("-------------------------------");
    for (Student student : studentRepository.findAll()) {
      System.out.println(student);
      for (Section section : student.sections) {
        System.out.println(section.course);
      }
    }
    System.out.println();

    System.out.println("Courses found with findAll():");
    System.out.println("-------------------------------");
    for (Course course : courseRepository.findAll()) {
      System.out.println(course);
      System.out.println(course.sections);
    }
    System.out.println();

    // fetch an individual Student
    System.out.println("Student found with findByFirstName('Alice'):");
    System.out.println("--------------------------------");
    System.out.println(studentRepository.findByFirstName("Alice"));

    System.out.println("Students found with findByLastName('Smith'):");
    System.out.println("--------------------------------");
    for (Student student : studentRepository.findByLastName("Smith")) {
      System.out.println(student);
    }
    System.out.println();
    System.out.println("Global Variables:");
    System.out.println(globals.getSchool());
    System.out.println(globals.getFreshman());
    System.out.println(globals.getSophomore());
    System.out.println(globals.getJunior());
    System.out.println(globals.getSenior());
  }

}
