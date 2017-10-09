package unbanner;

import org.springframework.data.annotation.Id;

public class Professor implements Storable {
    @Id
    public String id;
    public String name;


    public Professor() {
        this.name = "Nobody Special";
    }

    public Professor(String name) {
        this.name = name;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
