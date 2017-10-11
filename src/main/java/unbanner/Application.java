package unbanner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SectionRepository sectionRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    studentRepository.deleteAll();
    courseRepository.deleteAll();

    // save a couple of Courses and Students
    courseRepository.save(new Course("Computer Science I", 1050, 4, "CS", "Intro to Computer Science", "Learning basics of programming in computer science"));
    courseRepository.save(new Course("Computer Science II", 2050, 4, "CS", "Computer Science 2", "Learning Object Oriented Programming in Computer Science"));
    studentRepository.save(new Student("Alice", "Smith", 900123456));
    studentRepository.save(new Student("Bob", "Smith", 900123456));
    sectionRepository.save(new Section(1,courseRepository.findByName("Computer Science I")));
    sectionRepository.save(new Section(1,courseRepository.findByName("Computer Science II")));
    sectionRepository.save(new Section(2,courseRepository.findByName("Computer Science I")));

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


    alice.setCourses((List<Course>) new ArrayList<Course>(Arrays.asList(c1)));
    bob.setCourses((List<Course>) new ArrayList<Course>(Arrays.asList(c2,c1)));

    s1.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(alice)));
    s2.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob)));
    s3.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob)));

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
    // fetch all Students
    System.out.println("Students found with findAll():");
    System.out.println("-------------------------------");
    for (Student student : studentRepository.findAll()) {
      System.out.println(student);
      System.out.println(student.courses);
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

  }

}
