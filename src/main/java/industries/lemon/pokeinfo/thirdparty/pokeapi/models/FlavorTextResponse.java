package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.FlavorText;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FlavorTextResponse {
    private String flavorText;
    private LanguageResponse language;
    private NamedApiResourceResponse version;

    public FlavorText intoFlavorText() {
        FlavorText flavorText = new FlavorText();
        flavorText.setText(getFlavorText());
        flavorText.setLanguage(getLanguage().getName());
        flavorText.setVersion(getVersion().getName());
        return flavorText;
    }
}
