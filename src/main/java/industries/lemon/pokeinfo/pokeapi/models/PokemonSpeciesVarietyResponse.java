package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonSpeciesVarietyResponse {
    @JsonProperty("is_default")
    private boolean isDefault;
    private NamedApiResource pokemon;
}
