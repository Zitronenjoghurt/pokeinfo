package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "abilities")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(updatable = false, nullable = false)
    private int abilityId;

    @Column(nullable = false)
    private boolean isInitialized = false;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "abilities")
    @JsonIgnore
    private Set<Pokemon> pokemon;
}
