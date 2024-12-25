package EcoMonitoring.models;

import EcoMonitoring.models.Enums.CrisisDamageType;
import EcoMonitoring.models.Enums.EmissionType;
import jakarta.persistence.*;

@Entity
public class CrisisDamageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long Id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public CrisisDamageType crisisDamageType;

    @Column(name = "damageAssessment")
    public double damageAssessment;

    @Column(name = "yearOfCrisisDamage")
    public String yearOfCrisisDamage;

    @ManyToOne
    @JoinColumn(name = "objectId")
    public Objects object;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "substanceId")
    public Substances substance;

    //Constructors
    public CrisisDamageHistory() {
    }

    public CrisisDamageHistory(CrisisDamageType crisisDamageType, double damageAssessment, String yearOfCrisisDamage, Objects object, Substances substance) {
        this.crisisDamageType = crisisDamageType;
        this.damageAssessment = damageAssessment;
        this.yearOfCrisisDamage = yearOfCrisisDamage;
        this.object = object;
        this.substance = substance;
    }

    //Getters and Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }

    public CrisisDamageType getCrisisDamageType() {
        return crisisDamageType;
    }
    public void setCrisisDamageType(CrisisDamageType crisisDamageType) {
        this.crisisDamageType = crisisDamageType;
    }

    public double getDamageAssessment() {
        return damageAssessment;
    }
    public void setDamageAssessment(double damageAssessment) {
        this.damageAssessment = damageAssessment;
    }

    public String getYearOfCrisisDamage() {
        return yearOfCrisisDamage;
    }
    public void setYearOfCrisisDamage(String yearOfCrisisDamage) {
        this.yearOfCrisisDamage = yearOfCrisisDamage;
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
