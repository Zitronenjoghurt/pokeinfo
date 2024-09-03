package industries.lemon.pokeinfo.pokeapi.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbilityResponse extends BaseEntityResponse {
    private String name;
    private Boolean isMainSeries;
    @JsonProperty("generation")
    private NamedApiResource originGeneration;
    private List<LocalizedNameResponse> names;
    private List<VerboseEffectResponse> effectEntries;
    private List<AbilityEffectChange> effectChanges;
    private List<AbilityFlavorText> flavorTextEntries;
    private List<AbilityPokemon> pokemon;
}
