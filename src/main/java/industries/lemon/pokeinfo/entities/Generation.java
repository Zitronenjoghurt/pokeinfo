package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.GenerationResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "generations")
public class Generation extends BaseInitializableEntity<GenerationResponse> {
    @Column(nullable = false, updatable = false, unique = true)
    private int generationId;

    @OneToMany(mappedBy = "generation")
    @JsonIgnore
    private Set<Ability> abilities = new HashSet<>();

    @OneToMany(mappedBy = "generation")
    @JsonIgnore
    private Set<PokemonSpecies> species = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable
    private Set<LocalizedName> localizedNames = new HashSet<>();

    @Override
    public void applyResponse(GenerationResponse response) {}

    @Override
    public int getEntityId() {
        return getGenerationId();
    }

    @Override
    public void setEntityId(int id) {
        setGenerationId(id);
    }
}
