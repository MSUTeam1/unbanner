package unbanner;

import org.springframework.data.annotation.Id;

public class Course implements Storable{


    String department;
    int crn; //course number
    int credits;
    String description;
    String learningObjectives;
    int prerequisiteCreditLevel; //senior, graduate, etc.. ENUM these in global
    //what are corequesites.

    @Id
    private String id;


    public void setPrerequisiteCreditLevel(int prerequisiteCreditLevel) {
        this.prerequisiteCreditLevel = prerequisiteCreditLevel;
    }

    public Course(String department, int crn, int credits, String description,
                  String learningObjectives, int prerequisiteCreditLevel) {
        this.department = department;
        this.crn = crn;
        this.credits = credits;
        this.description = description;
        this.learningObjectives = learningObjectives;
        this.prerequisiteCreditLevel = prerequisiteCreditLevel;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLearningObjectives(String learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public void setCredits(int credits) {
        this.credits = credits;

    }

    public int getPrerequisiteCreditLevel() {
        return prerequisiteCreditLevel;
    }

    public String getLearningObjectives() {
        return learningObjectives;
    }

    public String getDepartment() {
        return department;
    }

    public String getDescription() {
        return description;
    }

    public int getCrn() {
        return crn;
    }

    public int getCredits() {

        return credits;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "department='" + department + '\'' +
                ", crn=" + crn +
                ", credits=" + credits +
                ", description='" + description + '\'' +
                ", learningObjectives='" + learningObjectives + '\'' +
                ", prerequisiteCreditLevel=" + prerequisiteCreditLevel +
                '}';
    }
}
