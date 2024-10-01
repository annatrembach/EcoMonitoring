package EcoMonitoring.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Substances")
public class Substances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private Type type;

    @OneToMany(mappedBy = "substance")
    public List<SubstanceHistory> substanceHistories;

    //Constructors
    public Substances() {
    }

    public Substances(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
}
