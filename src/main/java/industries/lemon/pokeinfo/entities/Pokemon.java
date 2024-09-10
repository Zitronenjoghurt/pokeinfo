package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pokemon")
public class Pokemon extends BaseEntity {
    @Column(updatable = false, nullable = false, unique = true)
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

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<PokemonAbility> pokemonAbilities;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private PokemonSprites sprites;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pokemon_id")
    private Set<PokemonStat> stats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pokemon_id")
    private Set<PokemonType> types;

    // ToDo: forms, game indices, held items, location area encounters, moves, past types, cries

    @OneToMany(mappedBy = "pokemon")
    @JsonIgnore
    private Set<PokemonSpeciesVariant> speciesVariants;

    public Optional<PokemonStat> getStatByName(String name) {
        return getStats().stream()
                .filter(stat -> stat.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public int getBaseStatTotal() {
        int total = 0;
        for (PokemonStat stat : getStats()) {
            total += stat.getBaseStat();
        }
        return total;
    }

    @Override
    public int getEntityId() {
        return getPokemonId();
    }

    @Override
    public void setEntityId(int id) {
        setPokemonId(id);
    }
}
