package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class TcgAttack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TcgPokemonType> cost;

    @Column(nullable = false)
    private int convertedEnergyCost;

    @Column(nullable = false)
    private String damage;

    @Column(nullable = false)
    private String text;
}
