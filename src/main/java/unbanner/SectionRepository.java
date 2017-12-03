package unbanner;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

public interface SectionRepository extends MongoRepository<Section, String> {

  public Section findById(String id);

  /*
   * returns a list of sections containing a particular course
   */
  public List<Section> findByCourse(Course course);

  /*
   * returns a list of sections containing a particular student
   */
  public List<Section> findByStudentsIn(Student student);

  public List<Section> findBySemester(Semester semester);

  public List<Section> findByProfessorIn(Professor professor);

  public List<Section> findByRoom(Room room);

}

