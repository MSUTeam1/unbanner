package unbanner;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SectionRepository extends MongoRepository<Section, String> {
  public Section findById(String id);

  public List<Section> findByCourse(Course course);

  //TODO figure this one out - find all sections containing a certain student
  //@Query(value="{ 'section.$id' : ?0 }")
  //public List<Section> findByStudentsContains(Student student);
}
