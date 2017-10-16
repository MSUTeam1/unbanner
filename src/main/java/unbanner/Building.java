package unbanner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class Building implements Storable {


    @Id
    public String id;

    public String name;
    public String description;
    @DBRef(lazy = true)
    public List<Room> rooms = new ArrayList<Room>();

    public Building(){
        this.name = "";
        this.description = "";
    }

    public Building(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getId() { return this.id; }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format(
                //Make more informative, later.
                "Building[id=%s, description='%s']",
                id, description);
    }

}

