package industries.lemon.pokeinfo.thirdparty.pokeapi.models;
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
    @JsonProperty("is_main_series")
    private Boolean isMainSeries;
    @JsonProperty("generation")
    private NamedApiResourceResponse originGeneration;
    private List<LocalizedNameResponse> names;
    private List<VerboseEffectResponse> effectEntries;
    private List<AbilityEffectChangeResponse> effectChanges; // ToDo
    private List<AbilityFlavorTextResponse> flavorTextEntries;
    private List<AbilityPokemonResponse> pokemon; // Todo (probably not)
}
