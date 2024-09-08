package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.PokemonSprites;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonSpritesResponse {
    private String frontDefault;
    private String frontShiny;
    private String frontFemale;
    private String frontShinyFemale;
    private String backDefault;
    private String backShiny;
    private String backFemale;
    private String backShinyFemale;

    public PokemonSprites intoPokemonSprites() {
        PokemonSprites pokemonSprites = new PokemonSprites();
        pokemonSprites.setFrontDefault(getFrontDefault());
        pokemonSprites.setFrontShiny(getFrontShiny());
        pokemonSprites.setFrontFemale(getFrontFemale());
        pokemonSprites.setFrontShinyFemale(getFrontShinyFemale());
        pokemonSprites.setBackDefault(getBackDefault());
        pokemonSprites.setBackShiny(getBackShiny());
        pokemonSprites.setBackFemale(getBackFemale());
        pokemonSprites.setBackShinyFemale(getBackShinyFemale());
        return pokemonSprites;
    }
}
