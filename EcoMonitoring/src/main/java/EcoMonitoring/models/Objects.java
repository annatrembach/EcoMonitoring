package EcoMonitoring.models;

import jakarta.persistence.*;

@Entity
public class Objects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String location;

    @ManyToOne
    public SubstanceHistory history;
}