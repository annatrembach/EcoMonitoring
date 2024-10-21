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

    @OneToMany(mappedBy = "object", fetch = FetchType.EAGER)
    public List<TaxesHistory> taxesHistories;

    //Constructors
    public Objects() {
    }

    public Objects(String name, String location, List<SubstanceHistory> substanceHistories, List<TaxesHistory> taxesHistories) {
        this.name = name;
        this.location = location;
        this.substanceHistories = substanceHistories;
        this.taxesHistories = taxesHistories;
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

    public List<SubstanceHistory> getSubstanceHistories() {
        return substanceHistories;
    }
    public void setSubstanceHistories(List<SubstanceHistory> substanceHistories) {
        this.substanceHistories = substanceHistories;
    }

    public List<TaxesHistory> getTaxesHistories() {
        return taxesHistories;
    }
    public void setTaxesHistories(List<TaxesHistory> taxesHistories) {
        this.taxesHistories = taxesHistories;
    }
}