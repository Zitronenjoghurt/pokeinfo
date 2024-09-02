package industries.lemon.pokeinfo.pokeapi.models;

import industries.lemon.pokeinfo.entities.Pokemon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonResponse {
    private int id;
    private String name;
    private int baseExperience;
    private int height;
    private boolean isDefault;
    private int order;
    private int weight;
    private List<PokemonAbility> abilities;

    public Pokemon intoPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonId(id);
        pokemon.setName(name);
        pokemon.setBaseExperience(baseExperience);
        pokemon.setHeight(height);
        pokemon.setDefault(isDefault);
        pokemon.setDexOrder(order);
        pokemon.setWeight(weight);
        return pokemon;
    }
}
