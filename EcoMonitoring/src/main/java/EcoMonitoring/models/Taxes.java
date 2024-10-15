package EcoMonitoring.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Taxes")
public class Taxes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "rate")
    public double rate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private TaxType taxType;

    @OneToOne
    @JoinColumn(name = "substance")
    public Substances substance;

    @OneToMany(mappedBy = "tax")
    public List<TaxesHistory> taxesHistories;

    //Constructors
    public Taxes() {
    }

    public Taxes(double rate, TaxType taxType, Substances substance, List<TaxesHistory> taxesHistories) {
        this.rate = rate;
        this.taxType = taxType;
        this.substance = substance;
        this.taxesHistories = taxesHistories;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

    public TaxType getTaxType() {
        return taxType;
    }
    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public Substances getSubstance() {
        return substance;
    }
    public void setSubstance(Substances substance) {
        this.substance = substance;
    }
}
