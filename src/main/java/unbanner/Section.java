package unbanner;

import org.springframework.data.annotation.Id;

import java.sql.Time;

public class Section implements Storable {
    @Id
    public String id;
    public int number;
    public String courseId;
    public String professorId;
    public String days;
    public Time time;


    public Section() {
        number = 1;
        courseId = "";
        professorId = "";
        time = new Time(0);
        days = "T/TH";
    }

    public Section(int number, String courseId, String professorId) {
        this.number = number;
        this.courseId = courseId;
        this.professorId = professorId;
    }

    public Section(int number, String courseId, String professorId, String days, Time time) {
        this(number, courseId, professorId);
        this.time = time;
        this.days = days;
    }
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


}
