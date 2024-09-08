package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import industries.lemon.pokeinfo.entities.TcgCard;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgRarity;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSubType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSuperType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TcgCardResponse extends BaseTcgEntityResponse {
    private String name;
    private TcgSuperType supertype;
    private Set<TcgSubType> subtypes;
    private String level;
    private String hp;
    private Set<TcgPokemonType> types;
    private Set<String> evolvesTo;
    private Set<TcgAttackResponse> attacks;
    private String number;
    private String artist;
    private TcgRarity rarity;
    private String flavorText;
    private Set<Integer> nationalPokedexNumbers;
    private TcgCardImagesResponse images;
    private TcgSetResponse set;

    public TcgCard intoCard() {
        TcgCard card = new TcgCard();
        card.setCardId(getId());
        card.setName(getName());
        card.setSuperType(getSupertype());
        card.setSubTypes(getSubtypes());
        card.setLevel(getLevel());
        card.setHp(getHp());
        card.setTypes(getTypes());
        card.setEvolvesTo(getEvolvesTo());
        card.setNumber(getNumber());
        card.setArtist(getArtist());
        card.setRarity(getRarity());
        card.setFlavorText(getFlavorText());
        card.setNationalPokedexNumbers(getNationalPokedexNumbers());
        return card;
    }
}
