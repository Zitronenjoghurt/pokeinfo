package industries.lemon.pokeinfo.pokeapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonAbility {
    private boolean isHidden;
    private int slot;
    private NamedApiResource ability;

    public String getName() {
        return ability.getName();
    }

    public Integer getId() {
        return ability.getId();
    }
}
