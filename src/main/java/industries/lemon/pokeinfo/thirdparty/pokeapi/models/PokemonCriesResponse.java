package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.PokemonCries;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonCriesResponse {
    private String latest;
    private String legacy;

    public PokemonCries intoPokemonCries() {
        PokemonCries pokemonCries = new PokemonCries();
        pokemonCries.setLatest(getLatest());
        pokemonCries.setLegacy(getLegacy());
        return pokemonCries;
    }
}
