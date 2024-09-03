package industries.lemon.pokeinfo.pokeapi.models;

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
    private NamedApiResource mainRegion;
    private List<NamedApiResource> moves;
    private List<NamedApiResource> pokemonSpecies;
    private List<NamedApiResource> types;
    private List<NamedApiResource> versionGroups;
}
