package unbanner;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SemesterRepository extends MongoRepository<Semester, String> {
    public Semester findByYearAndSeason(String year, String season);
}
