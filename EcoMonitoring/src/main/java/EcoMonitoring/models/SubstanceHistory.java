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

    @ManyToOne
    @JoinColumn(name = "objectId")
    public Objects object;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "substanceId")
    public Substances substance;

    //Constructors
    public SubstanceHistory() {
    }

    public SubstanceHistory(String yearOfObservation, double substanceValue, Objects object, Substances substance) {
        this.yearOfObservation = yearOfObservation;
        this.substanceValue = substanceValue;
        this.object = object;
        this.substance = substance;
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

    public Objects getObject() {
        return object;
    }
    public void setObject(Objects object) {
        this.object = object;
    }

    public Substances getSubstance() {
        return substance;
    }
    public void setSubstance(Substances substance) {
        this.substance = substance;
    }
}
