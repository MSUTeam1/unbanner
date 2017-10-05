package unbanner;

import org.springframework.data.annotation.Id;

public class Section implements Storable {
    @Id
    public String id;
    public int number;
    public String courseId;
    public String professorId;


    public Section() {
        number = 1;
        courseId = "";
        professorId = "";
    }

    public Section(int number, String courseId, String professorId) {
        this.number = number;
        this.courseId = courseId;
        this.professorId = professorId;
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
