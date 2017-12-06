package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService {

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  ProfessorRepository professorRepository;

  @Autowired
  SectionRepository sectionRepository;

  @Autowired
  CourseRepository courseRepository;

  @Override
  public void updateReferences(Section oldSection, Section tempSection) {

    System.out.println(oldSection);
    System.out.println(tempSection);

    System.out.println("old professor: "
        + oldSection.professor.getId().toHexString()
        + "\nnew professor: "
        + tempSection.professor.getId().toHexString());

    if (!oldSection.professor.getId().toHexString().equals(tempSection.professor.getId().toHexString())) { //professor changed
      int count = 0;
      for (Section section : oldSection.professor.getSections()) {
        if (section.id.toHexString().equals(tempSection.id.toHexString())) {
          System.out.println("removing: " + oldSection.professor.getSections().remove(count));
          professorRepository.save(oldSection.professor);
          break;
        } else {
        }
        count++;
      }
    } else {
    }
    tempSection.professor.sections.add(tempSection);
    professorRepository.save(tempSection.professor);

    System.out.println(oldSection.room.getName());
    System.out.println(tempSection.room.getName());
    if (!oldSection.room.getName().equals(tempSection.room.getName())) { //room changed
      int i = 0;
      for (i = 0 ; i < oldSection.room.getSectionList().size() ; i++ ) {
        if (oldSection.room.getSectionList().get(i).id.toHexString().equals(tempSection.id.toHexString())) {
          oldSection.room.getSectionList().remove(i);
          roomRepository.save(oldSection.room);
          break;
        } else {
        }
      }
    } else {
    }
    tempSection.room.sectionList.add(tempSection);
    roomRepository.save(tempSection.room);

    //if (!oldSection.semester) TODO Check if semester changed

    System.out.println("old students: " + oldSection.getStudents());
    System.out.println("new students: + " + tempSection.students);
    for (Student student : oldSection.getStudents()) { //removed previously listed student
      if (!tempSection.students.contains(student)) {
        student.sections.remove(oldSection);
        studentRepository.save(student);
      } else {
      }
    }

    for (Student student : tempSection.students) { //added new student
      if (!oldSection.students.contains(student)) {
        student.sections.add(tempSection);
        studentRepository.save(student);
      } else {
      }
    }

    sectionRepository.save(tempSection);
    courseRepository.save(tempSection.course);
  }
}
