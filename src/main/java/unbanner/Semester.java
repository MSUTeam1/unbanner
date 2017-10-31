package unbanner;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Semester implements Storable {


    @Id
    public ObjectId id;

    String season;
    int year;


    public Semester(String season, int year) {
        this.season = season;
        this.year = year;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }
}
