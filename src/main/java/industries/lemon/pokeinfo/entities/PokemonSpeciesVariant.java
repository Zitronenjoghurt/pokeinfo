package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pokemon_species_variant")
public class PokemonSpeciesVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private PokemonSpecies species;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    @Column
    private boolean isDefault;
}
