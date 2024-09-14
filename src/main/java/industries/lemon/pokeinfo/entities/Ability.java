package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.AbilityResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "abilities")
public class Ability extends BaseInitializableEntity<AbilityResponse> {
    @Column(updatable = false, nullable = false, unique = true)
    private int abilityId;

    @OneToMany(mappedBy = "ability")
    @JsonIgnore
    private Set<PokemonAbility> pokemonAbilities;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @Column
    private String name;

    @Column
    private Boolean isMainSeries;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable
    private Set<LocalizedName> localizedNames = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ability_id")
    private Set<VerboseEffect> verboseEffects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable
    private Set<AbilityFlavorText> flavorTexts = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Column
    // Reference to Pokémon which have this ability
    private Set<Integer> pokemonReferenceIds = new HashSet<>();

    @Override
    public void applyResponse(AbilityResponse response) {
        setName(response.getName());
        setIsMainSeries(response.getIsMainSeries());
    }

    @Override
    public int getEntityId() {
        return getAbilityId();
    }

    @Override
    public void setEntityId(int id) {
        setAbilityId(id);
    }
}
