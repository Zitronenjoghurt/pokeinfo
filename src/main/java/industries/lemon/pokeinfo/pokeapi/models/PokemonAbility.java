package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
