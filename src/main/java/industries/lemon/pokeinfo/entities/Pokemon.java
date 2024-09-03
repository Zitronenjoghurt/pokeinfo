package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pokemon")
public class Pokemon extends BaseEntity {
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

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PokemonAbility> pokemonAbilities;

    @Override
    public int getEntityId() {
        return getPokemonId();
    }

    @Override
    public void setEntityId(int id) {
        setPokemonId(id);
    }
}
