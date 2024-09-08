package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSubType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSuperType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class TcgCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String cardId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private TcgSuperType superType;

    @Column(nullable = false)
    private List<TcgSubType> subTypes;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int hp;

    @ElementCollection
    @CollectionTable
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TcgPokemonType> types;

    @ElementCollection
    @CollectionTable
    @Column(nullable = false)
    private List<String> evolvesTo;
}
