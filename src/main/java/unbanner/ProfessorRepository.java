package unbanner;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfessorRepository extends MongoRepository<Professor,String> {
    public Professor findById(String id);

    public Professor findByName(String name);

    public Professor save(Professor saved);
}
