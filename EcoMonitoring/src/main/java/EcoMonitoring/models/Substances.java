package EcoMonitoring.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Substances")
public class Substances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public PollutionType pollutionType;

    @OneToMany(mappedBy = "substance", fetch = FetchType.EAGER)
    public List<SubstanceHistory> substanceHistories;

    @OneToMany(mappedBy = "substance", fetch = FetchType.EAGER)
    public List<HealthRisk> healthRisks;

    @OneToOne(mappedBy = "substance", fetch = FetchType.EAGER)
    public Taxes tax;

    //Constructors
    public Substances() {
    }

    public Substances(PollutionType pollutionType, String name, List<SubstanceHistory> substanceHistories, Taxes tax) {
        this.pollutionType = pollutionType;
        this.name = name;
        this.substanceHistories = substanceHistories;
        this.tax = tax;
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

    public PollutionType getType() {
        return pollutionType;
    }
    public void setType(PollutionType pollutionType) {
        this.pollutionType = pollutionType;
    }


    public List<SubstanceHistory> getSubstanceHistories() {
        return substanceHistories;
    }
    public void setSubstanceHistories(List<SubstanceHistory> substanceHistories) {
        this.substanceHistories = substanceHistories;
    }

    public Taxes getTax() {
        return tax;
    }
    public void setTax(Taxes tax) {
        this.tax = tax;
    }
}
