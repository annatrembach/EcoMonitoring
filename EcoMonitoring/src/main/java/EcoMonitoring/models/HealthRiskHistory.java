package EcoMonitoring.models;

import EcoMonitoring.models.Enums.RiskLevel;
import jakarta.persistence.*;

@Entity
public class HealthRiskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long Id;

    @Column(name = "yearOfObservation")
    public String yearOfObservation;

    @Column(name = "resultRiskNumber")
    public double resultRiskNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "riskLevel")
    public RiskLevel riskLevel;

    @ManyToOne
    @JoinColumn(name = "objectId")
    public Objects object;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "healthRiskId")
    public HealthRisk healthRisk;

    //Getters and Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }

    public String getYearOfObservation() {
        return yearOfObservation;
    }
    public void setYearOfObservation(String yearOfObservation) {
        this.yearOfObservation = yearOfObservation;
    }

    public double getResultRiskNumber() {
        return resultRiskNumber;
    }
    public void setResultRiskNumber(double resultRiskNumber) {
        this.resultRiskNumber = resultRiskNumber;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Objects getObject() {
        return object;
    }
    public void setObject(Objects object) {
        this.object = object;
    }

    public HealthRisk getHealthRisk() {
        return healthRisk;
    }
    public void setHealthRisk(HealthRisk healthRisk) {
        this.healthRisk = healthRisk;
    }

    //Constructors
    public HealthRiskHistory() {
    }

    public HealthRiskHistory(String yearOfObservation, double resultRiskNumber, HealthRisk healthRisk, Objects object) {
        this.yearOfObservation = yearOfObservation;
        this.resultRiskNumber = resultRiskNumber;
        this.healthRisk = healthRisk;
        this.object = object;
    }
}
