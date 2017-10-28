package unbanner;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface StudentRepository extends MongoRepository<Student, String> {

  public Student findByFirstName(String firstName);

  public List<Student> findByLastName(String lastName);

  public Student findByFirstNameAndLastName(String firstName, String lastName);

}
