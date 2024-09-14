package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.AbilityFlavorText;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbilityFlavorTextResponse {
    private String flavorText;
    private LanguageResponse language;
    private NamedApiResourceResponse versionGroup;

    public AbilityFlavorText intoFlavorText() {
        AbilityFlavorText flavorText = new AbilityFlavorText();
        flavorText.setText(getFlavorText());
        flavorText.setLanguage(getLanguage().getName());
        flavorText.setVersionGroup(getVersionGroup().getName());
        return flavorText;
    }
}
