package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.VerboseEffect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VerboseEffectResponse {
    private String effect;
    private String shortEffect;
    private LanguageResponse language;

    public VerboseEffect intoVerboseEffect() {
        VerboseEffect verboseEffect = new VerboseEffect();
        verboseEffect.setEffect(effect);
        verboseEffect.setShortEffect(shortEffect);
        verboseEffect.setLanguage(language.getName());
        return verboseEffect;
    }
}
