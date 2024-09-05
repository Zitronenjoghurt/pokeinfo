package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.Pokemon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonResponse extends BaseEntityResponse {
    private String name;
    private int baseExperience;
    private int height;
    @JsonProperty("is_default")
    private boolean isDefault;
    private int order;
    private int weight;
    private List<PokemonAbilityResponse> abilities;
    private List<NamedApiResourceResponse> forms;
    private PokemonSpritesResponse sprites;
    private List<PokemonStatResponse> stats;
    private List<PokemonTypeResponse> types;

    public Pokemon intoPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonId(getId());
        pokemon.setName(name);
        pokemon.setBaseExperience(baseExperience);
        pokemon.setHeight(height);
        pokemon.setDefault(isDefault);
        pokemon.setDexOrder(order);
        pokemon.setWeight(weight);
        return pokemon;
    }
}
