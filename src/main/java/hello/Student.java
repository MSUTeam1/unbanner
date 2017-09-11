package hello;

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

    @Override
    public String toString() {
        return String.format(
                "Student[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}