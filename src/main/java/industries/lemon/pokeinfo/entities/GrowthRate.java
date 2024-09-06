package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "growth_rates")
public class GrowthRate extends BaseEntity {
    @Column(updatable = false, nullable = false)
    private int growthRateId;

    @Column(updatable = false, nullable = false)
    private String name;

    @Column(updatable = false, nullable = false, length = 512)
    private String formula;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable
    private Set<LocalizedDescription> localizedDescriptions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GrowthRateExperienceLevel> levels;

    @OneToMany(mappedBy = "growthRate", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PokemonSpecies> species;

    @Override
    public int getEntityId() {
        return getGrowthRateId();
    }

    @Override
    public void setEntityId(int id) {
        setGrowthRateId(id);
    }
}
