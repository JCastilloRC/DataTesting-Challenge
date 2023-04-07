package entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    @Id
    int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
