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
public class PokemonFormResponse extends BaseEntityResponse {
    private String name;
    @JsonProperty("order")
    private int sortOrder;
    private int formOrder;
    @JsonProperty("is_default")
    private boolean isDefault;
    @JsonProperty("is_battle_only")
    private boolean isBattleOnly;
    @JsonProperty("is_mega")
    private boolean isMega;
    private String formName;
    private NamedApiResourceResponse pokemon;
    private List<PokemonTypeResponse> types;
    private PokemonSpritesResponse sprites;
    private NamedApiResourceResponse versionGroup;
    private List<LocalizedNameResponse> names;
    private List<LocalizedNameResponse> formNames;
}
