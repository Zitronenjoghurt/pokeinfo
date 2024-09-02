package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import industries.lemon.pokeinfo.pokeapi.models.AbilityResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "abilities")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(updatable = false, nullable = false)
    private int abilityId;

    @Column(nullable = false)
    private boolean isInitialized = false;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "abilities")
    @JsonIgnore
    private Set<Pokemon> pokemon;

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

    public void initializeFromResponse(
            AbilityResponse abilityResponse,
            Generation generation,
            Set<LocalizedName> localizedNames,
            Set<VerboseEffect> verboseEffects
    ) {
        setName(abilityResponse.getName());
        setIsMainSeries(abilityResponse.getIsMainSeries());
        setGeneration(generation);
        setLocalizedNames(localizedNames);
        setVerboseEffects(verboseEffects);
        setInitialized(true);
    }
}
