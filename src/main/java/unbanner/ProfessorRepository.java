package unbanner;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProfessorRepository extends MongoRepository<Professor, String> {

  public Professor findByFirstName(String firstName);

  public List<Professor> findByLastName(String lastName);

  public Professor findByFirstNameAndLastName(String firstName, String lastName);

  public Professor findById(String id);

}


