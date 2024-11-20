package EcoMonitoring.models;

import jakarta.persistence.*;

@Entity
public class CompensationsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long Id;

    @Column(name = "yearOfLoss")
    public String yearOfLoss;

    @Column(name = "compensationAmount")
    public double compensationAmount;

    @ManyToOne
    @JoinColumn(name = "objectId")
    public Objects object;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compensationId")
    public Compensations compensation;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public EmissionType emissionType;

    //Constructors
    public CompensationsHistory() {
    }

    public CompensationsHistory(String yearOfLoss, double compensationAmount, Objects object, Compensations compensation, EmissionType emissionType) {
        this.yearOfLoss = yearOfLoss;
        this.compensationAmount = compensationAmount;
        this.object = object;
        this.compensation = compensation;
        this.emissionType = emissionType;
    }

    //Getters and Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }

    public String getYearOfLoss() {
        return yearOfLoss;
    }
    public void setYearOfLoss(String yearOfLoss) {
        this.yearOfLoss = yearOfLoss;
    }

    public double getCompensationAmount() {
        return compensationAmount;
    }
    public void setCompensationAmount(double compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public Objects getObject() {
        return object;
    }
    public void setObject(Objects object) {
        this.object = object;
    }

    public Compensations getCompensation() {
        return compensation;
    }
    public void setCompensation(Compensations compensation) {
        this.compensation = compensation;
    }

    public EmissionType getEmissionType() {
        return emissionType;
    }
    public void setEmissionType(EmissionType emissionType) {
        this.emissionType = emissionType;
    }
}

