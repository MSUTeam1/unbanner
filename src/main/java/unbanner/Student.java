package unbanner;

import org.springframework.data.annotation.Id;


public class Student {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    public Student() {}

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return String.format(
                "Student[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}