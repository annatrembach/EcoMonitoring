package EcoMonitoring.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
@Table(name = "Objects")
public class Objects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "location")
    public String location;

    @ManyToOne
    @JoinColumn(name = "historyId")
    public SubstanceHistory history;

    //Constructors
    public Objects() {
    }

    public Objects(String name, String location, SubstanceHistory history) {
        this.name = name;
        this.location = location;
        this.history = history;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public SubstanceHistory getHistory() {
        return history;
    }
    public void setHistory(SubstanceHistory history) {
        this.history = history;
    }
}