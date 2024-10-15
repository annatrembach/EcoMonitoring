package EcoMonitoring.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "object")
    public List<SubstanceHistory> substanceHistories;

    @OneToMany(mappedBy = "object")
    public List<TaxesHistory> taxesHistory;

    //Constructors
    public Objects() {
    }

    public Objects(String name, String location, List<SubstanceHistory> substanceHistories, List<TaxesHistory> taxesHistory) {
        this.name = name;
        this.location = location;
        this.substanceHistories = substanceHistories;
        this.taxesHistory = taxesHistory;
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



}