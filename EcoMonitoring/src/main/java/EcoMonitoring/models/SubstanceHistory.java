package EcoMonitoring.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SubstanceHistory")
public class SubstanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "yearOfObservation")
    public String yearOfObservation;

    @Column(name = "substanceValue")
    public double substanceValue;

    @OneToMany(mappedBy = "history")
    public List<Objects> objects;

    @OneToMany(mappedBy = "history")
    public List<Substances> substances;

    //Constructors
    public SubstanceHistory() {
    }

    public SubstanceHistory(String yearOfObservation, double substanceValue, List<Objects> objects, List<Substances> substances) {
        this.yearOfObservation = yearOfObservation;
        this.substanceValue = substanceValue;
        this.objects = objects;
        this.substances = substances;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getYearOfObservation() {
        return yearOfObservation;
    }
    public void setYearOfObservation(String yearOfObservation) {
        this.yearOfObservation = yearOfObservation;
    }

    public double getSubstanceValue() {
        return substanceValue;
    }
    public void setSubstanceValue(double substanceValue) {
        this.substanceValue = substanceValue;
    }

    public List<Objects> getObjects() {
        return objects;
    }
    public void setObjects(List<Objects> objects) {
        this.objects = objects;
    }
    public List<Substances> getSubstances() {
        return substances;
    }
    public void setSubstances(List<Substances> substances) {
        this.substances = substances;
    }
}
