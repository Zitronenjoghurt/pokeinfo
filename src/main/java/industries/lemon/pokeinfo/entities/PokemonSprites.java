package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pokemon_sprites")
public class PokemonSprites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private String frontDefault;

    @Column
    private String frontShiny;

    @Column
    private String frontFemale;

    @Column
    private String frontShinyFemale;

    @Column
    private String backDefault;

    @Column
    private String backShiny;

    @Column
    private String backFemale;

    @Column
    private String backShinyFemale;
}
