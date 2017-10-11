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

    Student alice = studentRepository.findByFirstName("Alice");
    Student bob = studentRepository.findByFirstName("Bob");
    Course c1 = courseRepository.findByName("Computer Science I");
    Course c2 = courseRepository.findByName("Computer Science II");

    alice.setCourses((List<Course>) new ArrayList<Course>(Arrays.asList(c1)));
    bob.setCourses((List<Course>) new ArrayList<Course>(Arrays.asList(c2)));

    c1.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(alice)));
    c2.setStudents((List<Student>) new ArrayList<Student>(Arrays.asList(bob)));

    studentRepository.save(alice);
    studentRepository.save(bob);

    courseRepository.save(c1);
    courseRepository.save(c2);

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
      System.out.println(course.students);
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
