package EcoMonitoring.models;

import jakarta.persistence.*;

@Entity
public class HealthRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long Id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    public AgentType agentType;

    @Column(name = "RfCorSFi")
    public double parameter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "substanceId")
    public Substances substance;

    //Getters ans Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }

    public AgentType getAgentType() {
        return agentType;
    }
    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public double getParameter() {
        return parameter;
    }
    public void setParameter(double parameter) {
        this.parameter = parameter;
    }

    public Substances getSubstance() {
        return substance;
    }
    public void setSubstance(Substances substance) {
        this.substance = substance;
    }

    //Constructors
    public HealthRisk() {
    }

    public HealthRisk(AgentType agentType, double parameter, Substances substance) {
        this.agentType = agentType;
        this.parameter = parameter;
        this.substance = substance;
    }
}
