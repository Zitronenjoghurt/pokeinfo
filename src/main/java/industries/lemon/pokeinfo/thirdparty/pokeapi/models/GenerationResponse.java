package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GenerationResponse extends BaseEntityResponse {
    private int id;
    private String name;
    private List<LocalizedNameResponse> names;
    private NamedApiResourceResponse mainRegion;
    private List<NamedApiResourceResponse> moves;
    private List<NamedApiResourceResponse> pokemonSpecies;
    private List<NamedApiResourceResponse> types;
    private List<NamedApiResourceResponse> versionGroups;
}
