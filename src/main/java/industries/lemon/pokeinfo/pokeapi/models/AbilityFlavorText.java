package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbilityFlavorText {
    private String flavorText;
    private LanguageResponse language;
    private NamedApiResource versionGroup;
}
