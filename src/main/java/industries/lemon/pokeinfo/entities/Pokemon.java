package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(updatable = false, nullable = false)
    private int pokemonId;

    @Column(updatable = false, nullable = false)
    private String name;

    @Column(updatable = false, nullable = false)
    private int baseExperience;

    @Column(updatable = false, nullable = false)
    private int height;

    @Column(updatable = false, nullable = false)
    private boolean isDefault;

    @Column(updatable = false, nullable = false)
    private int dexOrder;

    @Column(updatable = false, nullable = false)
    private int weight;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
        name = "pokemon_abilities",
        joinColumns = { @JoinColumn(name = "pokemon_id") },
        inverseJoinColumns = { @JoinColumn(name = "ability_id") }
    )
    private Set<Ability> abilities;
}
