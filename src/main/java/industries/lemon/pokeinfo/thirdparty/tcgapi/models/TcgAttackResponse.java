package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TcgAttackResponse {
    private String name;
    private List<TcgPokemonType> cost;
    private int convertedEnergyCost;
    private String damage;
    private String text;
}
