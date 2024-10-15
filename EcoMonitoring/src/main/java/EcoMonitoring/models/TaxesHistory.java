package EcoMonitoring.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TaxesHistory")
public class TaxesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "yearOfCalculation")
    public String yearOfCalculation;

    @Column(name = "sum")
    public double sum;

    @ManyToOne
    @JoinColumn(name = "objectId")
    public Objects object;

    @ManyToOne
    @JoinColumn(name = "taxId")
    public Taxes tax;


    //Constructors
    public TaxesHistory() {
    }

    public TaxesHistory(String yearOfCalculation, double sum, Objects object, Taxes tax) {
        this.yearOfCalculation = yearOfCalculation;
        this.sum = sum;
        this.object = object;
        this.tax = tax;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getYearOfCalculation() {
        return yearOfCalculation;
    }
    public void setYearOfCalculation(String yearOfCalculation) {
        this.yearOfCalculation = yearOfCalculation;
    }

    public double getSum() {
        return sum;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }

    public Objects getObject() {
        return object;
    }
    public void setObject(Objects object) {
        this.object = object;
    }

    public Taxes getTax() {
        return tax;
    }
    public void setTax(Taxes tax) {
        this.tax = tax;
    }
}
