package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbilityPokemonResponse {
    @JsonProperty("is_hidden")
    private boolean isHidden;
    private int slot;
    private NamedApiResourceResponse pokemon;
}
