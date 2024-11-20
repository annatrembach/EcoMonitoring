package EcoMonitoring.models;

import jakarta.persistence.*;

@Entity
public class Compensations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "MPC")
    public double MPC;

    @OneToOne
    @JoinColumn(name = "substanceId")
    public Substances substance;

    //Constructors
    public Compensations() {
    }

    public Compensations(double MPC, Substances substance) {
        this.MPC = MPC;
        this.substance = substance;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMPC() {
        return MPC;
    }
    public void setMPC(double MPC) {
        this.MPC = MPC;
    }

    public Substances getSubstance() {
        return substance;
    }
    public void setSubstance(Substances substance) {
        this.substance = substance;
    }
}
