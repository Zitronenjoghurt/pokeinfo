package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.PokemonType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonTypeResponse {
    private int slot;
    private NamedApiResourceResponse type;

    public PokemonType intoPokemonType() {
        PokemonType pokemonType = new PokemonType();
        pokemonType.setType(getType().getName());
        pokemonType.setSlot(getSlot());
        return pokemonType;
    }
}
