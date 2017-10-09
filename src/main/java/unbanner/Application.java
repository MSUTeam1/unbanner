package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private SectionRepository sectionRepository;


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    studentRepository.deleteAll();
    courseRepository.deleteAll();
    professorRepository.deleteAll();
    sectionRepository.deleteAll();

    // save a couple of Students
    studentRepository.save(new Student("Alice", "Smith", 900123456));
    studentRepository.save(new Student("Bob", "Smith", 900123456));


    Course cs1 = new Course("Computer Science I", 1050,4,"CS", "Intro to Computer Science","Learning basics of programming in computer science");
    Course cs2 = new Course("Computer Science II", 2050,4,"CS","Computer Science 2","Learning Object Oriented Programming in Computer Science");

    courseRepository.save(cs1);

    courseRepository.save(cs2);

    Professor shultz = new Professor("Jerry Shultz");
    Professor gurka = new Professor("Judith Gurka");
    professorRepository.save(gurka);
    professorRepository.save(shultz);

    // set course's professors
    cs1.setProfessor(shultz.id);
    cs2.setProfessor(shultz.id);



    // create the sections
    Section cs1section = new Section(1,cs1.id,shultz.id);
    Section cs1section2 = new Section(2,cs1.id,shultz.id);
    Section cs2section = new Section(1,cs2.id,shultz.id);

    // save sections to repository
    sectionRepository.save(cs1section);
    sectionRepository.save(cs1section2);
    sectionRepository.save(cs2section);


    // set courses sections
    cs1.setSection(cs1section.id);

    cs2.setSection(cs2section.id);


    // fetch all Students
    System.out.println("Students found with findAll():");
    System.out.println("-------------------------------");
    for (Student student : studentRepository.findAll()) {
      System.out.println(student);
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

    System.out.println("Sections found with findByCourseId('" + cs1.id + "'):");
    System.out.println("--------------------------------");
    for (Section section : sectionRepository.findByCourseId(cs1.id)) {
      System.out.println(section);
    }



  }

}
