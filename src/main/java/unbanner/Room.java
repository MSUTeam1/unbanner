package unbanner;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

/**
 *  Building ---1:M---< Room >---M:1-- Sections
 */
public class Room implements Storable {

    @Id
    public String id;
    public String name;
    public int size;
    @DBRef(lazy = true)
    public Building building;
    @DBRef(lazy = true)
    public List<Section> sectionList = new ArrayList<Section>();

    public Room(){
        this.name ="";
    }

    public Room(String name, int size){
        this.name =name;
        this.size = size;
    }

    @Override
    public String getId() {return this.id;}

    @Override
    public void setId(String Id) { this.id = Id; }

    public String getName() {return this.name; }

    public void setName(String name) {this.name = name; }

    public int getSize() {return this.size;}

    public void setSize(int size) {this.size = size;}
}
