package unbanner;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SectionRepository extends MongoRepository<Section, String> {
  public Section findById(String id);

  public List<Section> findByCourse(Course course);

  //need to figure this one out
  //@Query(value="{ 'section.$id' : ?0 }")
  //public List<Section> findByStudentsContains(Student student);
}
