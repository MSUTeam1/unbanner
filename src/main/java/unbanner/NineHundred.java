package unbanner;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class NineHundred {

  @Id
  private ObjectId id;

  @Getter
  @Setter
  public int seq;

  public NineHundred(int seq) {
    this.seq = seq;
  }

}
