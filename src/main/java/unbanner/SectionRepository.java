package unbanner;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SectionRepository extends MongoRepository<Section, String> {
    public Section findById(String id);
    public List<Section> findByCourse(Course course);
}
