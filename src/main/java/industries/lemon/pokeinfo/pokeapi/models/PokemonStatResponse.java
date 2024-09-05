package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.PokemonStat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonStatResponse {
    private NamedApiResourceResponse stat;
    private int effort;
    private int baseStat;

    public PokemonStat intoPokemonStat() {
        PokemonStat stat = new PokemonStat();
        stat.setName(getStat().getName());
        stat.setEffort(getEffort());
        stat.setBaseStat(getBaseStat());
        return stat;
    }
}
