package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GrowthRateResponse extends BaseEntityResponse {
    private String name;
    private String formula;
    private List<DescriptionResponse> descriptions;
    private List<GrowthRateExperienceLevelResponse> levels;
    private List<NamedApiResourceResponse> pokemon_species;
}
