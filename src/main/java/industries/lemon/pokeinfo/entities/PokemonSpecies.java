package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pokemon_species")
public class PokemonSpecies extends BaseEntity {
    @Column(updatable = false, nullable = false, unique = true)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "growth_rate_id")
    private GrowthRate growthRate;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable
    private Set<LocalizedName> localizedNames;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable
    private Set<FlavorText> flavorTexts;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<PokemonSpeciesVariant> variants;

    // ToDo: pokedex numbers, egg groups, color, shape, evolves from, evolution chain, habitat, genera

    @Column(nullable = false)
    private boolean tcgCardsInitialized = false;

    public String getOfficialArtworkUrl(boolean shiny) {
        if (!shiny) {
            return String.format("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/%s.png", getSpeciesId());
        }
        return String.format("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/%s.png", getSpeciesId());
    }

    public Optional<Pokemon> getDefaultVariant() {
        return variants.stream()
                .filter(PokemonSpeciesVariant::isDefault)
                .findFirst()
                .map(PokemonSpeciesVariant::getPokemon);
    }

    public String getSpecialStates() {
        List<String> specialStates = new ArrayList<>();
        if (isBaby()) {
            specialStates.add("Baby");
        }
        if (isLegendary()) {
            specialStates.add("Legendary");
        }
        if (isMythical()) {
            specialStates.add("Mythical");
        }
        if (specialStates.isEmpty()) {
            specialStates.add("None");
        }

        return String.join(", ", specialStates);
    }

    @Override
    public int getEntityId() {
        return getSpeciesId();
    }

    @Override
    public void setEntityId(int id) {
        setSpeciesId(id);
    }
}
