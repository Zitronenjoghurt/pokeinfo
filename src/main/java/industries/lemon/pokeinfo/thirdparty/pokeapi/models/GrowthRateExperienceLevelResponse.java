package industries.lemon.pokeinfo.thirdparty.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.GrowthRateExperienceLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GrowthRateExperienceLevelResponse {
    private int level;
    private int experience;

    public GrowthRateExperienceLevel intoGrowthRateExperienceLevel() {
        GrowthRateExperienceLevel level = new GrowthRateExperienceLevel();
        level.setLevel(getLevel());
        level.setExperience(getExperience());
        return level;
    }
}
