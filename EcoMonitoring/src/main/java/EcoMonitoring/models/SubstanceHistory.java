package EcoMonitoring.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SubstanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String yearOfObservation;
    public double substanceValue;

    @OneToMany(mappedBy = "history")
    public List<Objects> objects;

    @OneToMany(mappedBy = "history")
    public List<Substances> substances;
}
