package unbanner;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SemesterRepository extends MongoRepository<Semester, String> {
  public Semester findByYearAndSeason(String year, String season);
}
