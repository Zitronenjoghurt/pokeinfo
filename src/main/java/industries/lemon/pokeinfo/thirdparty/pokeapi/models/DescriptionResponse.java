package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.LocalizedDescription;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DescriptionResponse {
    private String description;
    private LanguageResponse language;

    public LocalizedDescription intoLocalizedDescription() {
        LocalizedDescription localizedDescription = new LocalizedDescription();
        localizedDescription.setDescription(getDescription());
        localizedDescription.setLanguage(getLanguage().getName());
        return localizedDescription;
    }
}
