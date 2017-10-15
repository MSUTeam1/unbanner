package unbanner;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


public class junkTestClass implements Storable {

    @Id
    public String id;
    public String name;
    public int size;
    @DBRef(lazy = true)
    public Building building;
    @DBRef(lazy = true)
    public List<Section> sectionList;



    @Override
    public String getId() {return this.id;}

    @Override
    public void setId(String Id) { this.id = Id; }

    public void addSection(Section section) {
        if (sectionList == null) {
            sectionList = new ArrayList<Section>();
        }
        this.sectionList.add(section);
    }

    public void setBuilding(Building building){
        this.building = building;
    }


}
