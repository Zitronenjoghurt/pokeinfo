package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pokemon_species")
public class PokemonSpecies extends BaseEntity {
    @Column(updatable = false, nullable = false)
    private int speciesId;

    @Column(updatable = false, nullable = false)
    private String name;

    @Column(updatable = false, nullable = false)
    private int dexOrder;

    @Column(updatable = false, nullable = false)
    private int genderRate;

    @Column(updatable = false, nullable = false)
    private int captureRate;

    @Column(updatable = false, nullable = false)
    private int baseHappiness;

    @Column(updatable = false, nullable = false)
    private boolean isBaby;

    @Column(updatable = false, nullable = false)
    private boolean isLegendary;

    @Column(updatable = false, nullable = false)
    private boolean isMythical;

    @Column(updatable = false, nullable = false)
    private int hatchCounter;

    @Column(updatable = false, nullable = false)
    private boolean hasGenderDifferences;

    @Column(updatable = false, nullable = false)
    private boolean formsSwitchable;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PokemonSpeciesVariant> variants;

    @Override
    public int getEntityId() {
        return getSpeciesId();
    }

    @Override
    public void setEntityId(int id) {
        setSpeciesId(id);
    }
}
