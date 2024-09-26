package EcoMonitoring.models;

import jakarta.persistence.*;

@Entity
public class Substances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public int type;

    @ManyToOne
    public SubstanceHistory history;

}
