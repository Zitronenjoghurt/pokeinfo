package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgRarity;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSubType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSuperType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class TcgCard extends BaseTcgEntity {
    @Column(nullable = false, unique = true)
    private String cardId;

    @Column
    private String name;

    @Column
    private TcgSuperType superType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Column
    @Enumerated(EnumType.STRING)
    private Set<TcgSubType> subTypes;

    @Column
    private String level;

    @Column
    private String hp;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Column
    @Enumerated(EnumType.STRING)
    private Set<TcgPokemonType> types;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Column
    private Set<String> evolvesTo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Column(nullable = false)
    private Set<TcgAttack> attacks;

    @Column
    private String number;

    @Column
    private String artist;

    @Column
    @Enumerated(EnumType.STRING)
    private TcgRarity rarity;

    @Column
    private String flavorText;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Column
    private Set<Integer> nationalPokedexNumbers;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private TcgCardImages images;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private TcgSet set;

    @Override
    public String getEntityId() {
        return getCardId();
    }

    @Override
    public void setEntityId(String id) {
        setCardId(id);
    }
}
