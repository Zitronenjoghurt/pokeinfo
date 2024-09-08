package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgRarity;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSubType;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgSuperType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TcgCardResponse {
    private String id;
    private String name;
    private TcgSuperType supertype;
    private List<TcgSubType> subtypes;
    private String level;
    private String hp;
    private List<TcgPokemonType> types;
    private List<String> evolvesTo;
    private List<TcgAttackResponse> attacks;
    private String number;
    private String artist;
    private TcgRarity rarity;
    private String flavorText;
    private List<Integer> nationalPokedexNumbers;
    private TcgCardImagesResponse images;
    private TcgSetResponse set;
}
