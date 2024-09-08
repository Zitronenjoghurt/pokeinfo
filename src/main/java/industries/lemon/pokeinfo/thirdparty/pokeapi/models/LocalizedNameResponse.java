package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.LocalizedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocalizedNameResponse {
    private String name;
    private LanguageResponse language;

    public LocalizedName intoLocalizedName() {
        LocalizedName localizedName = new LocalizedName();
        localizedName.setName(name);
        localizedName.setLanguage(language.getName());
        return localizedName;
    }
}
